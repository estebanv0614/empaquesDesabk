package com.empaques.desa.domain.dto;

public record ClientDto(
        Integer id,
        PersonDto person,
        String empresa,
        EstadoDto estado
) {}
