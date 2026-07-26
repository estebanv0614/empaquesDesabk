package com.empaques.desa.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record SolicitudCotizacionRequestDto(
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        String phone,
        @Email(message = "Email inválido")
        String mail,
        String city,
        String address,
        String observacion,
        @NotEmpty(message = "Debe incluir al menos un producto solicitado")
        @Valid
        List<DetalleSolicitudDto> detalles
) {
}
