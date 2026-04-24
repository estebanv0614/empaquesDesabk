package com.empaques.desa.domain.dto;

public record ClientDto(
        Integer id,
        PersonDto person,
        String company,
        StateDto state
) {}
