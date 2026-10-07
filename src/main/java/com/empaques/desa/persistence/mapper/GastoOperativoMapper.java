package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.GastoOperativoDto;
import com.empaques.desa.persistence.entity.GastoOperativoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {TipoGastoMapper.class, MetodoPagoMapper.class, EmployeeMapper.class, MaterialMapper.class})
public interface GastoOperativoMapper {
    GastoOperativoDto toDto(GastoOperativoEntity entity);
    List<GastoOperativoDto> toDtoList(Iterable<GastoOperativoEntity>entities);
}
