package com.empaques.desa.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoOperativoRequest(
        Integer idTipoGasto,
        BigDecimal monto,
        LocalDate fechaGasto,
        String descripcion,
        Integer idMetodoPago,
        Integer idEmpleado,
        Integer idMaterial,
        BigDecimal cantidad
) {}
