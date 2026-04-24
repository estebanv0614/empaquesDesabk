package com.empaques.desa.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeDto(
        Integer id,
        PersonDto person,
        String position,
        BigDecimal salary,
        LocalDate fechaIngreso,
        StateDto state
) {}
