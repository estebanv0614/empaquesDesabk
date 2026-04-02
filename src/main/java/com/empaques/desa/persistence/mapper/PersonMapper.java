package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.PersonDto;
import com.empaques.desa.persistence.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    @Mapping(source = "idPersona", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "mail", target = "mail")
    @Mapping(source = "aaddress", target = "aaddress")
    PersonDto toDto(PersonEntity entity);

    List<PersonDto> toDto(Iterable<PersonEntity> entities);

    @Mapping(source = "id", target = "idPersona")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "mail", target = "mail")
    @Mapping(source = "aaddress", target = "aaddress")
    PersonEntity toEntity(PersonDto dto);
}
