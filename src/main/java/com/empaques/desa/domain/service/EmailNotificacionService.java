package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.DocumentoComercialDto;
import com.empaques.desa.domain.dto.SolicitudCotizacionDto;
import com.lowagie.text.DocumentException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class EmailNotificacionService {
    private final JavaMailSender mailSender;
    private final  CotizacionPdfService cotizacionPdfService;

    @Value("${app.notificacion.email-admin}")
    private String emailAdmin;

    public EmailNotificacionService(JavaMailSender mailSender, CotizacionPdfService cotizacionPdfService) {
        this.mailSender = mailSender;
        this.cotizacionPdfService = cotizacionPdfService;
    }

    @Async
    public void notificarNuevaSolicitud(SolicitudCotizacionDto solicitud) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(emailAdmin);
            mensaje.setSubject("Nueva solicitud de cotización #" + solicitud.id());

            StringBuilder cuerpo = new StringBuilder();
            cuerpo.append("Se recibió una nueva solicitud de cotización.\n\n");
            cuerpo.append("Nombre: ").append(solicitud.name()).append("\n");
            cuerpo.append("Teléfono: ").append(solicitud.phone() != null ? solicitud.phone() : "No especificado").append("\n");
            cuerpo.append("Email: ").append(solicitud.mail() != null ? solicitud.mail() : "No especificado").append("\n");
            cuerpo.append("Ciudad: ").append(solicitud.city() != null ? solicitud.city() : "No especificada").append("\n");
            cuerpo.append("Dirección: ").append(solicitud.address() != null ? solicitud.address() : "No especificada").append("\n\n");
            cuerpo.append("Observaciones:\n").append(solicitud.observacion() != null ? solicitud.observacion() : "Ninguna").append("\n\n");

            cuerpo.append("Productos solicitados:\n");
            solicitud.detalles().forEach(detalle ->
                    cuerpo.append("- ").append(detalle.descripcionProducto())
                            .append(" (Cantidad estimada: ")
                            .append(detalle.cantidadEstimada() != null ? detalle.cantidadEstimada() : "No especificada")
                            .append(")\n")
            );
            cuerpo.append("\nIngresa al panel administrativo para revisar y responder esta solicitud.");
            mensaje.setText(cuerpo.toString());
            mailSender.send(mensaje);
        }  catch (Exception e) {
            System.err.println("Error al enviar correo de notificación de solicitud: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Async
    public void enviarCotizacionPdf(DocumentoComercialDto documento) {
        // Validación: si el cliente no tiene email, no hay a dónde mandarlo
        if (documento.client() == null || documento.client().person() == null
                || documento.client().person().email() == null
                || documento.client().person().email().isBlank()) {
            System.err.println("No se pudo enviar la cotización: el cliente no tiene email registrado.");
            return;
        }

        try {
            byte[] pdfBytes = cotizacionPdfService.generarCotizacion(documento);
            String emailCliente = documento.client().person().email();
            String nombreCliente = documento.client().person().name();

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // true = multipart, permite adjuntos

            helper.setTo(emailCliente);
            helper.setSubject("Tu cotización " + documento.numeroFactura() + " - Empaques Desa");

            String fechaTexto = documento.fechaEmision() != null
                    ? documento.fechaEmision().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    : "";

            String cuerpoHtml = "<p>Hola " + (nombreCliente != null ? nombreCliente : "") + ",</p>"
                    + "<p>Adjunto encontrarás tu cotización <strong>" + documento.numeroFactura() + "</strong>"
                    + (fechaTexto.isBlank() ? "" : " generada el " + fechaTexto) + ".</p>"
                    + "<p>Si tienes alguna pregunta, no dudes en contactarnos.</p>"
                    + "<br><p>Empaques Desa<br>empaquesdesa@gmail.com<br>321 382 6385</p>";

            helper.setText(cuerpoHtml, true); // true = es HTML

            helper.addAttachment("cotizacion-" + documento.numeroFactura() + ".pdf",
                    new org.springframework.core.io.ByteArrayResource(pdfBytes));

            mailSender.send(mimeMessage);
        } catch (MessagingException | IOException | DocumentException e) {
            System.err.println("Error al enviar el PDF de la cotización al cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
