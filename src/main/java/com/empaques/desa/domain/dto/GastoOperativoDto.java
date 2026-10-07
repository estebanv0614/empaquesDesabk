package com.empaques.desa.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoOperativoDto(
        Integer id,
        TipoGastoDto tipoGasto,
        BigDecimal monto,
        LocalDate fechaGasto,
        String descripcion,
        MetodoPagoDto metodoPago,
        EmployeeDto adminResponsable,
        String evidenciaUrl,
        MaterialDto material,
        BigDecimal cantidad
        ) {}

