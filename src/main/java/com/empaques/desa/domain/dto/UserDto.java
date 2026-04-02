package com.empaques.desa.domain.dto;

import java.util.Set;

public record UserDto(
        Integer id,
        PersonDto person,
        String username,
        String password,
        Boolean activo,
        Set<RolDto> roles
) {}
