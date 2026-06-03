package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.LoginDto;
import com.empaques.desa.domain.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<UserDto> getAll();
    Optional<UserDto> getById(Integer id);
    Optional<UserDto> getByUsername(String username);
    UserDto save(UserDto dto);
    Optional<UserDto> update(Integer id, UserDto dto);
    boolean delete(Integer id);
    boolean restore(Integer id);
    UserDto login(LoginDto dto);
    boolean deactivate(Integer id);
    boolean activate(Integer id);

}
