package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<UserDto> getAll();
    Optional<UserDto> getById(Integer id);
    UserDto seva(UserDto dto);
    Optional<UserDto> update(Integer id, UserDto dto);
    boolean delete(Integer id);
}
