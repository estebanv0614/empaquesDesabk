package com.empaques.desa.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserDto(
        Integer id,

        PersonDto person,

        @NotBlank(message = "Username obligatorio")
        String username,

        @NotBlank(message = "Password obligatoria")
        @Size(min = 6)
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String password,
        Boolean activo,
        Set<RolDto> roles
) { }
