package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.DetalleDocumentoDto;
import com.empaques.desa.persistence.entity.DetalleDocumentoEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DocumentoComercialMapper.class, BolsaMapper.class})
public interface DetalleDocumentoMapper {
    @Mapping(source = "idDetalle", target = "id")
    @Mapping(source = "documentoComercial", target = "documentoComercial")
    @Mapping(source = "bolsa", target = "bolsa")
    DetalleDocumentoDto toDto(DetalleDocumentoEntity entity);

    List<DetalleDocumentoDto> toDtoList(Iterable<DetalleDocumentoEntity> entities);

    @InheritInverseConfiguration
    @Mapping(target = "documentoComercial", ignore = true)
    @Mapping(target = "bolsa", ignore = true)
    DetalleDocumentoEntity toEntity(DetalleDocumentoDto dto);
}
