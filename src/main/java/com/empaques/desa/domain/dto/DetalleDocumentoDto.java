package com.empaques.desa.domain.dto;

import java.math.BigDecimal;

public record DetalleDocumentoDto(
        Integer id,
        DocumentoComercialDto documentoComercial,
        BolsaDto bolsa,
        BigDecimal cantidad,
        BigDecimal precioUnitarioSnapshot,
        BigDecimal subtotal
) {}
