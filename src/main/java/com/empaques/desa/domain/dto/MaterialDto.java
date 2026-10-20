package com.empaques.desa.domain.dto;

import java.math.BigDecimal;

public record MaterialDto(
        Integer id,
        String name,
        String unidadMedida,
        String categoria,
        BigDecimal stockActual,
        BigDecimal stockMinimo
) {}
