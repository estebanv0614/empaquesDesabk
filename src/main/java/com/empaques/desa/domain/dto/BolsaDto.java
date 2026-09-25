package com.empaques.desa.domain.dto;

import java.math.BigDecimal;

public record BolsaDto(
        Integer id,
        String name,
        String description,
        String tipo,
        BigDecimal anchoCm,
        BigDecimal largoCm,
        BigDecimal calibre,
        BigDecimal precioBase,
        String imagenUrl,
        EstadoDto estado
        ) {}
