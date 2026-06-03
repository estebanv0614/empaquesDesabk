package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.PersonDto;
import com.empaques.desa.persistence.entity.PersonEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TipoDocumentoMapper.class})
public interface PersonMapper {

    @Mapping(source = "idPerson", target = "id")
    @Mapping(source = "tipoDocumento", target = "tipoDocumento")
    PersonDto toDto(PersonEntity entity);

    List<PersonDto> toDtoList(Iterable<PersonEntity> entities);

    @InheritInverseConfiguration
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "tipoDocumento", ignore = true)
    PersonEntity toEntity(PersonDto dto);
}
