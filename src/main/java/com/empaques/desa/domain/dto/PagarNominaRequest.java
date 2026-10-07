package com.empaques.desa.domain.dto;

public record PagarNominaRequest(
        Integer idMetodoPago,
        Integer idEmpleado
) {}
