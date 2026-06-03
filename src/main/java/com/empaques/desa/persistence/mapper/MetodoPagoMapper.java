package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.MetodoPagoDto;
import com.empaques.desa.persistence.entity.MetodoPagoEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MetodoPagoMapper {
    @Mapping(source = "idPaymentMethod", target = "id")
    MetodoPagoDto toDto(MetodoPagoEntity entity);

    List<MetodoPagoDto> toDtoList(Iterable<MetodoPagoEntity> entities);

    @InheritInverseConfiguration
    MetodoPagoEntity toEntity(MetodoPagoDto dto);
}
