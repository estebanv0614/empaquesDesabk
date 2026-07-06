package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.DocumentoComercialDto;
import com.empaques.desa.persistence.entity.DocumentoComercialEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        TipoDocumentoMapper.class,
        ClientMapper.class,
        UserMapper.class,
        MetodoPagoMapper.class,
        EstadoMapper.class})
public interface DocumentoComercialMapper {
    @Mapping(source = "idDocumento", target = "id")
    @Mapping(source = "tipoDocumento", target = "tipoDocumento")
    @Mapping(source = "client", target = "client")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "metodoPago", target = "metodoPago")
    @Mapping(source = "estado", target = "estado")
    @Mapping(source = "referenciaCotizacion.idDocumento", target = "referenciaCotizacionId")
    DocumentoComercialDto toDto(DocumentoComercialEntity entity);

    List<DocumentoComercialDto> toDtoList(Iterable<DocumentoComercialEntity> entities);

    @InheritInverseConfiguration
    @Mapping(target = "tipoDocumento", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "metodoPago", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "referenciaCotizacion", ignore = true)
    DocumentoComercialEntity toEntity(DocumentoComercialDto dto);
}
