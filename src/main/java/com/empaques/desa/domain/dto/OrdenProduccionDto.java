package com.empaques.desa.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrdenProduccionDto(
        Integer id,
        BolsaDto bolsa,
        BigDecimal cantidadProducir,
        LocalDateTime fechaProduccion,
        EstadoDto estado
) {}
