package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.DetallePedidoDto;
import com.empaques.desa.domain.dto.PedidoDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelPedidoService {
    public byte[] generarExcel(List<PedidoDto> pedidos) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("pedidos");

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle moneyStyle = workbook.createCellStyle();
            moneyStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));

            String[] columnas = {
                    "N° Pedido", "Cliente", "Documento cliente", "Teléfono cliente", "Email cliente",
                    "Empresa cliente", "Vendedor", "Fecha pedido", "Fecha entrega estimada",
                    "Estado", "Pagado", "Fecha pago", "Método de pago",
                    "Producto", "Cantidad", "Precio unitario", "Subtotal línea",
                    "Subtotal pedido", "Impuestos", "Total pedido", "Observaciones"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerStyle);
            }

            DateTimeFormatter fechaFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter fechaHoraFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            int rowIdx = 1;

            for (PedidoDto pedido : pedidos) {
                String clienteNombre = pedido.client() != null && pedido.client().person() != null
                        ? pedido.client().person().name() : "-";
                String clienteDocumento = pedido.client() != null && pedido.client().person() != null
                        ? pedido.client().person().documentNumber() : "-";
                String clienteTelefono = pedido.client() != null && pedido.client().person() != null
                        ? pedido.client().person().phone() : "-";
                String clienteEmail = pedido.client() != null && pedido.client().person() != null
                        ? pedido.client().person().email() : "-";
                String clienteEmpresa = pedido.client() != null ? pedido.client().empresa() : "-";
                String vendedor = pedido.userVendedor() != null ? pedido.userVendedor().username() : "-";
                String fechaPedidoStr = pedido.fechaPedido() != null ? pedido.fechaPedido().format(fechaHoraFmt) : "-";
                String fechaEntregaStr = pedido.fechaEntregaEstimada() != null ? pedido.fechaEntregaEstimada().format(fechaFmt) : "-";
                String estadoStr = pedido.estado() != null ? pedido.estado().name() : "-";
                String pagadoStr = Boolean.TRUE.equals(pedido.pagado()) ? "SI" : "NO";
                String fechaPagoStr = pedido.fechaPago() != null ? pedido.fechaPago().format(fechaHoraFmt) : "-";
                String metodoPagoStr = pedido.metodoPago() != null ? pedido.metodoPago().name() : "-";
                String observacionStr = pedido.observacion() != null ? pedido.observacion() : "";

                List<DetallePedidoDto> detalles = pedido.detalles();

                if (detalles == null || detalles.isEmpty()) {
                    Row row = sheet.createRow(rowIdx++);
                    llenarDatosGenerales(row, moneyStyle, pedido, clienteNombre, clienteDocumento, clienteTelefono,
                            clienteEmail, clienteEmpresa, vendedor, fechaPedidoStr, fechaEntregaStr, estadoStr,
                            pagadoStr, fechaPagoStr, metodoPagoStr, observacionStr);
                    row.createCell(13).setCellValue("(sin productos)");
                } else {
                    for (DetallePedidoDto detalle : detalles) {
                        Row row = sheet.createRow(rowIdx++);
                        llenarDatosGenerales(row, moneyStyle, pedido, clienteNombre, clienteDocumento, clienteTelefono,
                                clienteEmail, clienteEmpresa, vendedor, fechaPedidoStr, fechaEntregaStr, estadoStr,
                                pagadoStr, fechaPagoStr, metodoPagoStr, observacionStr);

                        String nombreProducto = detalle.bolsa() != null
                                ? (detalle.bolsa().name() != null ? detalle.bolsa().name() : detalle.bolsa().tipo())
                                : "-";
                        row.createCell(13).setCellValue(nombreProducto);
                        row.createCell(14).setCellValue(detalle.cantidad() != null ? detalle.cantidad() : 0);

                        Cell precioCell = row.createCell(15);
                        precioCell.setCellValue(detalle.precioUnitarioVenta() != null ? detalle.precioUnitarioVenta().doubleValue() : 0);
                        precioCell.setCellStyle(moneyStyle);

                        Cell subtotalLineaCell = row.createCell(16);
                        subtotalLineaCell.setCellValue(detalle.subtotalLinea() != null ? detalle.subtotalLinea().doubleValue() : 0);
                        subtotalLineaCell.setCellStyle(moneyStyle);
                    }
                }
            }
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }
    private void llenarDatosGenerales(Row row, CellStyle moneyStyle, PedidoDto pedido,
                                      String clienteNombre, String clienteDocumento, String clienteTelefono, String clienteEmail,
                                      String clienteEmpresa, String vendedor, String fechaPedidoStr, String fechaEntregaStr,
                                      String estadoStr, String pagadoStr, String fechaPagoStr, String metodoPagoStr, String observacionStr) {

        row.createCell(0).setCellValue(pedido.numeroPedido());
        row.createCell(1).setCellValue(clienteNombre);
        row.createCell(2).setCellValue(clienteDocumento);
        row.createCell(3).setCellValue(clienteTelefono);
        row.createCell(4).setCellValue(clienteEmail);
        row.createCell(5).setCellValue(clienteEmpresa);
        row.createCell(6).setCellValue(vendedor);
        row.createCell(7).setCellValue(fechaPedidoStr);
        row.createCell(8).setCellValue(fechaEntregaStr);
        row.createCell(9).setCellValue(estadoStr);
        row.createCell(10).setCellValue(pagadoStr);
        row.createCell(11).setCellValue(fechaPagoStr);
        row.createCell(12).setCellValue(metodoPagoStr);

        // Columnas 17-20: totales del pedido (se repiten en cada línea de producto)
        Cell subtotalCell = row.createCell(17);
        subtotalCell.setCellValue(pedido.subtotal() != null ? pedido.subtotal().doubleValue() : 0);
        subtotalCell.setCellStyle(moneyStyle);

        Cell impuestosCell = row.createCell(18);
        impuestosCell.setCellValue(pedido.impuestos() != null ? pedido.impuestos().doubleValue() : 0);
        impuestosCell.setCellStyle(moneyStyle);

        Cell totalCell = row.createCell(19);
        totalCell.setCellValue(pedido.total() != null ? pedido.total().doubleValue() : 0);
        totalCell.setCellStyle(moneyStyle);

        row.createCell(20).setCellValue(observacionStr);
    }
}
