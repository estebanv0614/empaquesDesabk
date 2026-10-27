package com.empaques.desa.domain.dto;

import com.empaques.desa.domain.TipoCliente;

public record ClientDto(
        Integer id,
        PersonDto person,
        String empresa,
        EstadoDto estado,
        TipoCliente tipoCliente,
        String ciudad,
        String direccionEntrega,
        String observaciones
) {}
