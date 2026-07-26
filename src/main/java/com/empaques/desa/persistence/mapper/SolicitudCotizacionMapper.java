package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.SolicitudCotizacionDto;
import com.empaques.desa.persistence.entity.SolicitudCotizacionEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EstadoMapper.class, DetalleSolicitudMapper.class})
public interface SolicitudCotizacionMapper {
    @Mapping(source = "idSolicitud", target = "id")
    @Mapping(source = "estado", target = "estado")
    @Mapping(source = "detalles", target = "detalles")
    SolicitudCotizacionDto toDto(SolicitudCotizacionEntity entity);

    List<SolicitudCotizacionDto> toDtoList(Iterable<SolicitudCotizacionEntity> entities);
}
