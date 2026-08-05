package com.empaques.desa.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoDto(
        Integer id,
        String numeroPedido,
        ClientDto client,
        UserDto userVendedor,
        EstadoDto estado,
        LocalDateTime fechaPedido,
        LocalDate fechaEntregaEstimada,
        BigDecimal subtotal,
        BigDecimal impuestos,
        BigDecimal total,
        MetodoPagoDto metodoPago,
        String observacion,
        List<DetallePedidoDto> detalles
) {
}
