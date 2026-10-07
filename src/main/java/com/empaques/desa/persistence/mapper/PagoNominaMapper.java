package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.PagoNominaDto;
import com.empaques.desa.persistence.entity.PagoNominaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        EmployeeMapper.class, MetodoPagoMapper.class,})
public interface PagoNominaMapper {
    PagoNominaDto toDto(PagoNominaEntity entity);
    List<PagoNominaDto> toDtoList(Iterable<PagoNominaEntity> entities);
}
