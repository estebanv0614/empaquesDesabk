package com.empaques.desa.domain.dto;

import java.math.BigDecimal;

public record ConsumoSugeridoDto(Integer idMaterial, String nombre,
                                 String unidadMedida, BigDecimal cantidad) {}
