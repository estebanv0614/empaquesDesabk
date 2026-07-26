package com.empaques.desa.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record DetalleSolicitudDto(
        Integer id,
        @NotBlank(message = "La descripcion del producto  es obligatoria ")
        String descripcionProducto,
        String cantidadEstimada
        ) {
}
