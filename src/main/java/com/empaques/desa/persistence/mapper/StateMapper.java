package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.StateDto;
import com.empaques.desa.persistence.entity.StateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StateMapper {
    @Mapping(source = "idState", target = "id")
    StateDto toDto(StateEntity entity);

    List<StateDto> toDtoList(Iterable<StateEntity> entities);

    @Mapping(source = "id", target = "idState")
    StateEntity toEntity(StateDto dto);
}
