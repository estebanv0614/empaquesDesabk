package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.EmployeeDto;
import com.empaques.desa.persistence.entity.EmployeeEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PersonMapper.class, EstadoMapper.class})
public interface EmployeeMapper {
    @Mapping(source = "idEmpleado", target = "id")
    @Mapping(source = "person", target = "person")
    @Mapping(source = "estado", target = "estado")
    EmployeeDto toDto(EmployeeEntity entity);

    List<EmployeeDto> toDtoList(Iterable<EmployeeEntity> entities);

    @InheritInverseConfiguration
    //@Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "estado", ignore = true)
    EmployeeEntity toEntity(EmployeeDto dto);
}
