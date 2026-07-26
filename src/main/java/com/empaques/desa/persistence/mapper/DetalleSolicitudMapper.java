package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.DetalleSolicitudDto;
import com.empaques.desa.persistence.entity.DetalleSolicitudEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetalleSolicitudMapper {
    @Mapping(source = "idDetalle", target = "id")
    DetalleSolicitudDto toDto(DetalleSolicitudEntity entity);

    List<DetalleSolicitudDto> toDtoList(Iterable<DetalleSolicitudEntity> entities);
}
