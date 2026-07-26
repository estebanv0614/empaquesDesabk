package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.*;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class CotizacionPdfService {
    private static final Color VERDE_BORDE = new Color(139, 195, 74);
    private static final Font FONT_LABEL = new Font(Font.HELVETICA, 9, Font.BOLD);
    private static final Font FONT_NORMAL = new Font(Font.HELVETICA, 9, Font.NORMAL);
    private static final Font FONT_TITULO = new Font(Font.HELVETICA, 14, Font.BOLD);
    private static final Font FONT_TABLA_HEADER = new Font(Font.HELVETICA, 9, Font.BOLD);
    private static final Font FONT_NOTA = new Font(Font.HELVETICA, 8, Font.NORMAL);
    private static final Font FONT_TOTAL_LABEL = new Font(Font.HELVETICA, 10, Font.BOLD);
    private static final Font FONT_TOTAL_VALOR = new Font(Font.HELVETICA, 10, Font.BOLD);

    public byte[] generarCotizacion(DocumentoComercialDto documento) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.setPageEvent(new BordePageEvent());

        document.open();

        agregarEncabezado(document, documento);
        agregarIntroduccion(document);
        agregarTablaItems(document, documento.detalles());
        agregarTotales(document, documento);
        agregarNotas(document);
        agregarFirma(document);

        document.close();
        return baos.toByteArray();
    }

    // ===================== ENCABEZADO =====================
    private void agregarEncabezado(Document document, DocumentoComercialDto doc) throws DocumentException, IOException {
        PdfPTable header = new PdfPTable(2);
        header.setWidthPercentage(100);
        header.setWidths(new float[]{60, 40});
        PdfPTable datosCliente = new PdfPTable(2);
        datosCliente.setWidths(new float[]{25, 75});

        ClientDto client = doc.client();
        String nombreCliente = "";
        String documentoCliente = "";
        String direccionCliente = "";
        String telefonoCliente = "";

        if (client != null && client.person() != null) {
            nombreCliente = client.person().name();
            if (client.empresa() != null && !client.empresa().isBlank()) {
                nombreCliente += " - " + client.empresa();
            }
            documentoCliente = client.person().documentNumber();
            direccionCliente = client.person().address();
            telefonoCliente = client.person().phone();
        }

        agregarFila(datosCliente, "FECHA:", doc.fechaEmision().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        agregarFila(datosCliente, "NOMBRE:", nombreCliente);
        agregarFila(datosCliente, "NIT O CC:", documentoCliente);
        agregarFila(datosCliente, "DIRECCION:", direccionCliente);
        agregarFila(datosCliente, "TELEFONO:", telefonoCliente);

        PdfPCell celdaIzquierda = new PdfPCell(datosCliente);
        celdaIzquierda.setBorder(Rectangle.NO_BORDER);
        celdaIzquierda.setVerticalAlignment(Element.ALIGN_TOP);
        header.addCell(celdaIzquierda);
        PdfPTable derecha = new PdfPTable(1);
        try {
            Image logo = Image.getInstance(new ClassPathResource("static/images/logo.png").getURL());
            logo.scaleToFit(100, 60);
            logo.setAlignment(Element.ALIGN_CENTER);
            PdfPCell celdaLogo = new PdfPCell(logo, false);
            celdaLogo.setBorder(Rectangle.NO_BORDER);
            celdaLogo.setHorizontalAlignment(Element.ALIGN_CENTER);
            derecha.addCell(celdaLogo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String tipoDocTexto = "NÚMERO DE FACTURA";
        Paragraph tituloTipo = new Paragraph("NÚMERO DE FACTURA", FONT_LABEL);
        tituloTipo.setAlignment(Element.ALIGN_CENTER);
        PdfPCell celdaTitulo = new PdfPCell(tituloTipo);
        celdaTitulo.setBorder(Rectangle.NO_BORDER);
        celdaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        derecha.addCell(celdaTitulo);

        PdfPCell celdaNumero = new PdfPCell(new Phrase(doc.numeroFactura(), FONT_TITULO));
        celdaNumero.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaNumero.setPadding(6);
        derecha.addCell(celdaNumero);

        PdfPCell celdaDerecha = new PdfPCell(derecha);
        celdaDerecha.setBorder(Rectangle.NO_BORDER);
        header.addCell(celdaDerecha);

        document.add(header);
        document.add(Chunk.NEWLINE);
    }

    private void agregarFila(PdfPTable table, String label, String valor) {
        PdfPCell celdaLabel = new PdfPCell(new Phrase(label, FONT_LABEL));
        celdaLabel.setBorder(Rectangle.NO_BORDER);
        table.addCell(celdaLabel);

        PdfPCell celdaValor = new PdfPCell(new Phrase(valor != null ? valor : "", FONT_NORMAL));
        celdaValor.setBorder(Rectangle.NO_BORDER);
        table.addCell(celdaValor);
    }

    // ===================== INTRODUCCIÓN =====================
    private void agregarIntroduccion(Document document) throws DocumentException {
        Paragraph intro = new Paragraph(
                "Atendiendo su amable solicitud estamos enviando cotización de los productos requeridos, " +
                        "para nosotros es un placer poner nuestra compañía a su servicio.",
                FONT_NORMAL
        );
        intro.setSpacingAfter(10);
        document.add(intro);
    }

    // ===================== TABLA DE ITEMS =====================
    private void agregarTablaItems(Document document, List<DetalleDocumentoDto> detalles) throws DocumentException {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{8, 13, 44, 17, 18});

        agregarCeldaHeader(table, "ITEM");
        agregarCeldaHeader(table, "CANTIDAD");
        agregarCeldaHeader(table, "DESCRIPCIÓN");
        agregarCeldaHeader(table, "VALOR UN");
        agregarCeldaHeader(table, "SUBTOTAL");

        int itemNum = 1;
        int minFilas = 5;

        for (DetalleDocumentoDto detalle : detalles) {
            table.addCell(celdaSimple(String.valueOf(itemNum++), Element.ALIGN_CENTER));
            table.addCell(celdaSimple(detalle.cantidad().stripTrailingZeros().toPlainString(), Element.ALIGN_CENTER));
            table.addCell(celdaSimple(construirDescripcionBolsa(detalle.bolsa()), Element.ALIGN_LEFT));
            table.addCell(celdaSimple("$ " + formatMoney(detalle.precioUnitarioSnapshot()), Element.ALIGN_RIGHT));
            table.addCell(celdaSimple("$ " + formatMoney(detalle.subtotal()), Element.ALIGN_RIGHT));
        }

        for (int i = detalles.size(); i < minFilas; i++) {
            table.addCell(celdaSimple("", Element.ALIGN_CENTER));
            table.addCell(celdaSimple("", Element.ALIGN_CENTER));
            table.addCell(celdaSimple("", Element.ALIGN_LEFT));
            table.addCell(celdaSimple("", Element.ALIGN_RIGHT));
            table.addCell(celdaSimple("", Element.ALIGN_RIGHT));
        }

        document.add(table);
    }

    private String construirDescripcionBolsa(BolsaDto bolsa) {
        if (bolsa == null) return "";
        return String.format(
                "BOLSA %s - %sx%s cm - Calibre %s",
                bolsa.tipo() != null ? bolsa.tipo() : "",
                formatDecimal(bolsa.anchoCm()),
                formatDecimal(bolsa.largoCm()),
                formatDecimal(bolsa.calibre())
        );
    }

    private String formatDecimal(BigDecimal valor) {
        return valor != null ? valor.stripTrailingZeros().toPlainString() : "";
    }

    private void agregarCeldaHeader(PdfPTable table, String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, FONT_TABLA_HEADER));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new Color(230, 230, 230));
        cell.setPadding(5);
        table.addCell(cell);
    }

    private PdfPCell celdaSimple(String texto, int alineacion) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, FONT_NORMAL));
        cell.setHorizontalAlignment(alineacion);
        cell.setPadding(4);
        cell.setMinimumHeight(18);
        return cell;
    }

    private String formatMoney(BigDecimal valor) {
        return String.format("%,.0f", valor);
    }

    // ===================== TOTALES =====================
    private void agregarTotales(Document document, DocumentoComercialDto doc) throws DocumentException {
        PdfPTable totalesTable = new PdfPTable(2);
        totalesTable.setWidthPercentage(45);
        totalesTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalesTable.setWidths(new float[]{50, 50});
        totalesTable.setSpacingBefore(6);

        agregarFilaTotal(totalesTable, "SUBTOTAL:", doc.subtotal(), false);
        agregarFilaTotal(totalesTable, "IVA:", doc.iva(), false);
        agregarFilaTotal(totalesTable, "TOTAL:", doc.total(), true);

        document.add(totalesTable);
        document.add(Chunk.NEWLINE);
    }

    private void agregarFilaTotal(PdfPTable table, String label, BigDecimal valor, boolean destacado) {
        Font fontLabel = destacado ? FONT_TOTAL_LABEL : FONT_LABEL;
        Font fontValor = destacado ? FONT_TOTAL_VALOR : FONT_NORMAL;

        PdfPCell celdaLabel = new PdfPCell(new Phrase(label, fontLabel));
        celdaLabel.setBorder(destacado ? Rectangle.TOP : Rectangle.NO_BORDER);
        celdaLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        celdaLabel.setPadding(4);
        table.addCell(celdaLabel);

        PdfPCell celdaValor = new PdfPCell(new Phrase("$ " + formatMoney(valor != null ? valor : BigDecimal.ZERO), fontValor));
        celdaValor.setBorder(destacado ? Rectangle.TOP : Rectangle.NO_BORDER);
        celdaValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
        celdaValor.setPadding(4);
        table.addCell(celdaValor);
    }

    // ===================== NOTAS FIJAS =====================
    private void agregarNotas(Document document) throws DocumentException {
        Paragraph recuerde = new Paragraph("RECUERDE:", FONT_LABEL);
        document.add(recuerde);

        String[] notas = {
                "Recuerde que",
                "1. Material de la bolsa con adictivo en cumplimiento normativo con el sello de impresión",
                "2. La forma de pago es el 50% al inicio y a la entrega el otro 50%",
                "3. La cantidad total puede ser mas o menos el 10% de lo solicitado.",
                "4. Todo trabajo se inicia con su respectiva Orden de Compra y aprobación del cliente.",
                "5. El plazo de entrega es de 10 a 15 días hábiles.",
                "6. El valor de los fletes lo cubre el cliente"
        };

        for (String nota : notas) {
            document.add(new Paragraph(nota, FONT_NOTA));
        }
        document.add(Chunk.NEWLINE);
    }

    // ===================== FIRMA FIJA =====================
    private void agregarFirma(Document document) throws DocumentException, IOException {
        PdfPTable firmaTable = new PdfPTable(3);
        firmaTable.setWidthPercentage(100);
        firmaTable.setWidths(new float[]{55, 30, 15});

        // Columna vacía (para empujar el contenido a la derecha)
        PdfPCell vacia = new PdfPCell(new Phrase(""));
        vacia.setBorder(Rectangle.NO_BORDER);
        firmaTable.addCell(vacia);

        // Columna con los datos de firma/contacto
        PdfPTable datosFirma = new PdfPTable(1);
        agregarLineaFirma(datosFirma, "JAVIER SALDAÑA", FONT_LABEL);
        agregarLineaFirma(datosFirma, "EMPAQUES DESA", FONT_NORMAL);
        agregarLineaFirma(datosFirma, "empaquesdesa@gmail.com", FONT_NORMAL);
        agregarLineaFirma(datosFirma, "CEL: 321 382 6385 - 310 861 2970", FONT_NORMAL);
        agregarLineaFirma(datosFirma, "CALLE 34 # 68I - 99 SUR CARVAJAL", FONT_NORMAL);

        PdfPCell celdaFirma = new PdfPCell(datosFirma);
        celdaFirma.setBorder(Rectangle.NO_BORDER);
        firmaTable.addCell(celdaFirma);

        // Columna con el logo pequeño
        PdfPCell celdaLogo = new PdfPCell();
        celdaLogo.setBorder(Rectangle.NO_BORDER);
        celdaLogo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaLogo.setHorizontalAlignment(Element.ALIGN_CENTER);

        try {
            Image logoFirma = Image.getInstance(new ClassPathResource("static/images/logo.png").getURL());
            logoFirma.scaleToFit(45, 45);
            celdaLogo.addElement(logoFirma);
        } catch (Exception e) {
            e.printStackTrace();
        }

        firmaTable.addCell(celdaLogo);

        document.add(firmaTable);
    }

    private void agregarLineaFirma(PdfPTable table, String texto, Font font) {
        Paragraph p = new Paragraph(texto, font);
        p.setAlignment(Element.ALIGN_RIGHT);
        PdfPCell cell = new PdfPCell(p);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
    }

    // ===================== BORDE VERDE DE LA PÁGINA =====================
    private static class BordePageEvent extends PdfPageEventHelper {
        private Image logoMarcaAgua;

        public BordePageEvent() {
            try {
                logoMarcaAgua = Image.getInstance(new ClassPathResource("static/images/logo.png").getURL());
            } catch (Exception e) {
                logoMarcaAgua = null;
            }
        }

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            if (logoMarcaAgua == null) return;

            try {
                PdfContentByte cb = writer.getDirectContentUnder();
                PdfGState gs = new PdfGState();
                gs.setFillOpacity(0.12f);
                gs.setStrokeOpacity(0.05f);

                cb.saveState();
                cb.setGState(gs);

                float pageWidth = document.getPageSize().getWidth();
                float pageHeight = document.getPageSize().getHeight();
                float logoWidth = 300;
                float logoHeight = logoWidth * (logoMarcaAgua.getHeight() / logoMarcaAgua.getWidth());
                float x = (pageWidth - logoWidth) / 2;
                float y = (pageHeight - logoHeight) / 2;

                cb.addImage(logoMarcaAgua, logoWidth, 0, 0, logoHeight, x, y);
                cb.restoreState();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Rectangle rect = new Rectangle(
                    document.left() - 10, document.bottom() - 10,
                    document.right() + 10, document.top() + 10
            );
            rect.setBorder(Rectangle.BOX);
            rect.setBorderWidth(2);
            rect.setBorderColor(VERDE_BORDE);
            cb.rectangle(rect);
        }
    }
}
