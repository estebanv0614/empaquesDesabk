package com.empaques.desa.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagoNominaDto(
        Integer id,
        EmployeeDto employee,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        BigDecimal salarioBase,
        BigDecimal bonificaciones,
        BigDecimal deducciones,
        BigDecimal totalPagar,
        Boolean pagado,
        LocalDate fechaPago,
        MetodoPagoDto metodoPago,
        String observacion
) {}
