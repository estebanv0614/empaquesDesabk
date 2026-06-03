package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.EstadoDto;
import com.empaques.desa.persistence.entity.EstadoEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EstadoMapper {
    @Mapping(source = "idState", target = "id")
    EstadoDto toDto(EstadoEntity entity);

    List<EstadoDto> toDtoList(Iterable<EstadoEntity> entities);

    @InheritInverseConfiguration
    EstadoEntity toEntity(EstadoDto dto);
}
