package com.empaques.desa.domain.dto;

import jakarta.validation.constraints.*;

public record PersonDto(
        Integer id,
        @NotNull(message = "Tipo documento obligatorio")
        TipoDocumentoDto tipoDocumento,

        @NotBlank(message = "Documento obligatorio")
        String documentNumber,

        @NotBlank(message = "Nombre obligatorio")
        String name,
        String phone,

        @Email(message = "Email inválido")
        String email,
        String address
) {}
