package com.empaques.desa.domain.dto;

import java.math.BigDecimal;

public record MaterialFaltanteDto(
        Integer idMaterial,
        String nombre,
        BigDecimal requerido,
        BigDecimal disponible,
        BigDecimal faltante
) {}
