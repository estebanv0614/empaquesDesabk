package com.empaques.desa.domain.dto;

import java.math.BigDecimal;

public record BolsaDto(
        Integer id,
        String tipoBolsa,
        BigDecimal anchoCm,
        BigDecimal largoCm,
        BigDecimal caliber,
        BigDecimal priceBase,
        StateDto state
) {}
