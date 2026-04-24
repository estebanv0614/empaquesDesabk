package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.ClientDto;
import com.empaques.desa.persistence.entity.ClientEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PersonMapper.class, StateMapper.class})
public interface ClientMapper {
    @Mapping(source = "idClient", target = "id")
    @Mapping(source = "person", target = "person")
    @Mapping(source = "state", target = "state")
    ClientDto toDto(ClientEntity entity);

    List<ClientDto> toDtoList(Iterable<ClientEntity> entities);

    @InheritInverseConfiguration
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "state", ignore = true)
    ClientEntity toEntity(ClientDto dto);

}
