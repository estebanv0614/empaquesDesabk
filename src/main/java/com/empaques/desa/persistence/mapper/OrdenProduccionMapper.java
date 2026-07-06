package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.OrdenProduccionDto;
import com.empaques.desa.persistence.entity.OrdenProduccionEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BolsaMapper.class, EstadoMapper.class})
public interface OrdenProduccionMapper {
    @Mapping(source = "idOrden", target = "id")
    @Mapping(source = "bolsa", target = "bolsa")
    @Mapping(source = "estado", target = "estado")
    OrdenProduccionDto toDto(OrdenProduccionEntity entity);

    List<OrdenProduccionDto> toDtoList(Iterable<OrdenProduccionEntity> entities);

    @InheritInverseConfiguration
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "bolsa", ignore = true)
    @Mapping(target = "estado", ignore = true)
    OrdenProduccionEntity toEntity(OrdenProduccionDto dto);
}
