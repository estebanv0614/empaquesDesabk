package com.empaques.desa.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record DocumentoComercialDto(
        Integer id,
        String numeroFactura,
        TipoDocumentoDto tipoDocumento,
        ClientDto client,
        UserDto user,
        LocalDateTime fechaEmision,
        BigDecimal subtotal,
        BigDecimal iva,
        BigDecimal total,
        MetodoPagoDto metodoPago,
        EstadoDto estado,
        Integer referenciaCotizacionId,
        String observaciones,
        List<DetalleDocumentoDto> detalles
        ) {}
