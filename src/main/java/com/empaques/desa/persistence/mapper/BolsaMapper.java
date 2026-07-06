package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.BolsaDto;
import com.empaques.desa.persistence.entity.BolsaEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EstadoMapper.class})
public interface BolsaMapper {
    @Mapping(source = "idBolsa", target = "id")
    @Mapping(source = "estado", target = "estado")
    BolsaDto toDto(BolsaEntity entity);

    List<BolsaDto> toDtoList(Iterable<BolsaEntity> entities);

    @InheritInverseConfiguration
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "estado", ignore = true)
    BolsaEntity toEntity(BolsaDto dto);
}
