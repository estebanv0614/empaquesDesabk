package com.empaques.desa.domain.dto;

public record ResumenPedidosDto(
        long hoy,
        long semanaActual,
        long mesActual,
        long anioActual
) {
}
