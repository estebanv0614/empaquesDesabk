package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.UserDto;
import com.empaques.desa.persistence.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RolMapper.class, PersonMapper.class})
public interface UserMapper {
    @Mapping(source = "idUser", target = "id")
    @Mapping(source = "person", target = "person")
    @Mapping(source = "roles", target = "roles")
    UserDto toDto(UserEntity user);

    List<UserDto> toDtoList(Iterable<UserEntity> users);

    @InheritInverseConfiguration
    @Mapping(source = "person", target = "person")
    UserEntity toEntity(UserDto userDto);
}
