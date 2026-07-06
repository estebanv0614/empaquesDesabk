package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.MovimientoBolsaDto;
import com.empaques.desa.persistence.entity.MovimientoBolsaEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MaterialMapper.class,
        OrdenProduccionMapper.class, UserMapper.class})
public interface MovimientoBolsaMapper {
    @Mapping(source = "idMovimientoBolsa", target = "id")
    @Mapping(source = "material", target = "material")
    @Mapping(source = "ordenProduccion", target = "ordenProduccion")
    @Mapping(source = "user", target = "user")
    MovimientoBolsaDto toDto(MovimientoBolsaEntity entity);

    List<MovimientoBolsaDto> toDtoList(Iterable<MovimientoBolsaEntity> entities);

    @InheritInverseConfiguration
    @Mapping(target = "material", ignore = true)
    @Mapping(target = "ordenProduccion", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "fechaMovimiento", ignore = true)
    MovimientoBolsaEntity toEntity(MovimientoBolsaDto dto);
}
