package com.empaques.desa.domain.dto;

import java.math.BigDecimal;

public record MaterialDto(
        Integer id,
        String name,
        String unidadMedida,
        BigDecimal costoUnitario,
        BigDecimal stockActual
) {}
