package com.empaques.desa.domain.dto;

import org.springframework.web.multipart.MultipartFile;

public record PagarNominaForm(
        Integer idMetodoPago,
        Integer idEmpleado,
        MultipartFile evidencia
) {
    public PagarNominaRequest toRequest() {
        return new PagarNominaRequest(idMetodoPago, idEmpleado);
    }
}
