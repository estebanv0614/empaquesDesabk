package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.EmployeeDto;
import com.empaques.desa.persistence.entity.EmployeeEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PersonMapper.class, StateMapper.class})
public interface EmployeeMapper {
    @Mapping(source = "idEmployee", target = "id")
    @Mapping(source = "person", target = "person")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "position", target = "position")
    @Mapping(source = "salary", target = "salary")
    @Mapping(source = "fechaIngreso", target = "fechaIngreso")
    EmployeeDto toDto(EmployeeEntity entity);

    List<EmployeeDto> toDtoList(Iterable<EmployeeEntity> entities);

    @InheritInverseConfiguration
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "state", ignore = true)
    EmployeeEntity toEntity(EmployeeDto dto);
}
