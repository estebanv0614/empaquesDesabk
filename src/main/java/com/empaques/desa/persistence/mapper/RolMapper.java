package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.RolDto;
import com.empaques.desa.persistence.entity.RolEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface RolMapper {
    @Mapping(source = "idRol", target = "id")
    @Mapping(source = "name", target = "rol")
    RolDto toDto(RolEntity entity);

    List<RolDto> toDtoList(Iterable<RolEntity> roles);
    Set<RolDto> toDtoSet(Set<RolEntity> roles);

    @InheritInverseConfiguration
    RolEntity toEntity(RolDto rolDto);
}
