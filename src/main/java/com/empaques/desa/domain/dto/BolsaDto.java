package com.empaques.desa.domain.dto;

import java.math.BigDecimal;

public record BolsaDto(
        Integer id,
        String tipo,
        BigDecimal anchoCm,
        BigDecimal largoCm,
        BigDecimal calibre,
        BigDecimal precioBase,
        BigDecimal stockActual,
        EstadoDto estado
        ) {}
