package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.BolsaDto;
import com.empaques.desa.persistence.entity.BolsaEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StateMapper.class})
public interface BolsaMapper {
    @Mapping(source = "idBolsa", target = "id")
    @Mapping(source = "tipoBolsa", target = "tipoBolsa")
    @Mapping(source = "anchoCm", target = "anchoCm")
    @Mapping(source = "largoCm", target = "largoCm")
    @Mapping(source = "caliber", target = "caliber")
    @Mapping(source = "priceBase", target = "priceBase")
    @Mapping(source = "state", target = "state")
    BolsaDto toDto(BolsaEntity entity);

    List<BolsaDto> toDtoList(Iterable<BolsaEntity> entities);

    @InheritInverseConfiguration
    @Mapping(target = "state", ignore = true)
    BolsaEntity toEntity(BolsaDto dto);

}
