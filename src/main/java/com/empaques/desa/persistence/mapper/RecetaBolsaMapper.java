package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.RecetaBolsaDto;
import com.empaques.desa.persistence.entity.RecetaBolsaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BolsaMapper.class, MaterialMapper.class})
public interface RecetaBolsaMapper {
    @Mapping(source = "bolsa", target = "bolsa")
    @Mapping(source = "material", target = "material")
    RecetaBolsaDto toDto(RecetaBolsaEntity entity);

    List<RecetaBolsaDto> toDtoList(Iterable<RecetaBolsaEntity> entities);
}
