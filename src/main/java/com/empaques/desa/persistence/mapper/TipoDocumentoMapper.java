package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.TipoDocumentoDto;
import com.empaques.desa.persistence.entity.TipoDocumentoEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TipoDocumentoMapper {
    @Mapping(source = "idTypeDocument", target = "id")
    TipoDocumentoDto toDto(TipoDocumentoEntity entity);

    List<TipoDocumentoDto> toDtoList(Iterable<TipoDocumentoEntity> entities);

    @InheritInverseConfiguration
    TipoDocumentoEntity toEntity(TipoDocumentoDto dto);
}
