package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.MaterialDto;
import com.empaques.desa.persistence.entity.MaterialEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MaterialMapper {
    @Mapping(source = "idMaterial", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "costoUnitario", target = "costoUnitario")
    @Mapping(source = "stockCurrent", target = "stockCurrent")
    MaterialDto toDto(MaterialEntity entity);

    List<MaterialDto> toDtoList(Iterable<MaterialEntity> entities);

    @Mapping(source = "id", target = "idMaterial")
    MaterialEntity toEntity(MaterialDto dto);
}
