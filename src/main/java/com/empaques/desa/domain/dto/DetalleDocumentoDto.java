package com.empaques.desa.domain.dto;

import java.math.BigDecimal;

public record DetalleDocumentoDto(
        Integer id,
        Integer idDocumento,
        BolsaDto bolsa,
        BigDecimal cantidad,
        BigDecimal precioUnitarioSnapshot,
        BigDecimal subtotal
) {}
