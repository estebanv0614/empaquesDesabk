package com.empaques.desa.domain.dto;

import com.empaques.desa.domain.TipoMovimiento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoInventarioDto(
        Integer id,
        MaterialDto material,
        OrdenProduccionDto ordenProduccion,
        UserDto user,
        TipoMovimiento tipoMovimiento,
        BigDecimal cantidad,
        BigDecimal stockAntes,
        BigDecimal stockDespues,
        LocalDateTime fechaMovimiento,
        String referencia,
        String observacion
) {}
