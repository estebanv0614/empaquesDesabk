package com.empaques.desa.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrdenProduccionDto(
        Integer id,
        DetallePedidoDto detallePedido,
        UserDto user,
        BigDecimal cantidadProducida,
        BigDecimal cantidadMerma,
        LocalDateTime fechaFin,
        String observacion,
        BigDecimal cantidadProducir,
        LocalDateTime fechaProduccion,
        EstadoDto estado,
        String numeroPedido
) {}
