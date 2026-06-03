package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.TipoGastoDto;
import com.empaques.desa.persistence.entity.TipoGastoEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TipoGastoMapper {
    @Mapping(source = "idTipoGasto", target = "id")
    TipoGastoDto toDto(TipoGastoEntity entity);

    List<TipoGastoDto> toDtoList(Iterable<TipoGastoEntity> entities);

    @InheritInverseConfiguration
    TipoGastoEntity toEntity(TipoGastoDto dto);
}
