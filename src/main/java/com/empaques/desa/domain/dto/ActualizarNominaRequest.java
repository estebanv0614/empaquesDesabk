package com.empaques.desa.domain.dto;

import java.math.BigDecimal;

public record ActualizarNominaRequest(
        BigDecimal bonificaciones,
        BigDecimal deducciones,
        String observacion
) {}
