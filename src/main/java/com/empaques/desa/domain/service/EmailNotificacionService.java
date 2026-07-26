package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.SolicitudCotizacionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificacionService {
    private final JavaMailSender mailSender;

    @Value("${app.notificacion.email-admin}")
    private String emailAdmin;

    public EmailNotificacionService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void notificarNuevaSolicitud(SolicitudCotizacionDto solicitud) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailAdmin);
        message.setSubject("Nueva solicitud de cotización #" + solicitud.id());

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

        message.setText(cuerpo.toString());
        mailSender.send(message);
    }
}
