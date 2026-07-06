package com.empaques.desa.domain.dto;

import java.math.BigDecimal;

public record RecetaBolsaDto(
        BolsaDto bolsa,
        MaterialDto material,
        BigDecimal cantidadRequerida
) {}
