package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.MovimientoInventarioDto;
import com.empaques.desa.persistence.entity.MovimientoInventarioEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MaterialMapper.class,
        OrdenProduccionMapper.class, UserMapper.class})
public interface MovimientoInventarioMapper {
    @Mapping(source = "idMovimiento", target = "id")
    @Mapping(source = "material", target = "material")
    @Mapping(source = "ordenProduccion", target = "ordenProduccion")
    @Mapping(source = "user", target = "user")
    MovimientoInventarioDto toDto(MovimientoInventarioEntity entity);

    List<MovimientoInventarioDto> toDtoList(Iterable<MovimientoInventarioEntity> entities);

    @InheritInverseConfiguration
    @Mapping(target = "material", ignore = true)
    @Mapping(target = "ordenProduccion", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "fechaMovimiento", ignore = true)
    MovimientoInventarioEntity toEntity(MovimientoInventarioDto dto);
}
