package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.MaterialDto;
import com.empaques.desa.persistence.entity.MaterialEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MaterialMapper {
    @Mapping(source = "idMaterial", target = "id")
    MaterialDto toDto(MaterialEntity entity);

    List<MaterialDto> toDtoList(Iterable<MaterialEntity> entities);

    @InheritInverseConfiguration
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    MaterialEntity toEntity(MaterialDto dto);
}
