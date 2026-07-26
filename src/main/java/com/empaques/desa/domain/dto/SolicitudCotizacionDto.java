package com.empaques.desa.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

public record SolicitudCotizacionDto(
        Integer id,
        String name,
        String phone,
        String mail,
        String city,
        String address,
        LocalDateTime fechaSolicitud,
        String observacion,
        EstadoDto estado,
        List<DetalleSolicitudDto> detalles
) {
}
