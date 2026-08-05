package com.empaques.desa.domain.dto;

import java.math.BigDecimal;

public record DetallePedidoDto(
        Integer id,
        BolsaDto bolsa,
        Integer cantidad,
        BigDecimal precioUnitarioVenta,
        BigDecimal subtotalLinea

) {
}
