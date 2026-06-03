package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.LoginDto;
import com.empaques.desa.domain.dto.RolDto;
import com.empaques.desa.domain.dto.UserDto;
import com.empaques.desa.domain.exception.InvalidPasswordException;
import com.empaques.desa.domain.exception.UserAlreadyExistsException;
import com.empaques.desa.domain.exception.UserNotFoundException;
import com.empaques.desa.domain.repository.UserRepository;
import com.empaques.desa.persistence.crud.CrudPersonEntity;
import com.empaques.desa.persistence.crud.CrudRolEntity;
import com.empaques.desa.persistence.crud.CrudUserEntity;
import com.empaques.desa.persistence.entity.RolEntity;
import com.empaques.desa.persistence.entity.UserEntity;
import com.empaques.desa.persistence.mapper.UserMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserEntityRepository implements UserRepository {
    private final CrudUserEntity crudUser;
    private final UserMapper userMapper;
    private final CrudPersonEntity crudPerson;
    private final CrudRolEntity crudRol;

    public UserEntityRepository(CrudUserEntity crudUser, UserMapper userMapper, CrudPersonEntity crudPerson, CrudRolEntity crudRol) {
        this.crudUser = crudUser;
        this.userMapper = userMapper;
        this.crudPerson = crudPerson;
        this.crudRol = crudRol;
    }

    @Override
    public List<UserDto> getAll() {
        return userMapper.toDtoList(crudUser.findAll());
    }

    @Override
    public Optional<UserDto> getById(Integer id) {
        return crudUser.findById(id)
                .map(userMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getByUsername(String username) {
        return crudUser.findByUsername(username).map(userMapper::toDto);
    }

    @Override
    public UserDto save(UserDto dto) {
        Integer idPerson = dto.person().id();
        if (crudUser.existsByPerson_IdPerson(idPerson)){
            throw new UserAlreadyExistsException(idPerson);
        }
        UserEntity entity = userMapper.toEntity(dto);
        entity.setPerson(
                crudPerson.findById(dto.person().id())
                        .orElseThrow(() -> new RuntimeException("El person no existe"))
        );
        Set<RolEntity> roles = dto.roles()
                .stream()
                .map(RolDto::id)
                .map(id ->
                        crudRol.findById(id)
                                .orElseThrow(()->
                                        new RuntimeException("Rol no encontrado: " + id))
                ).collect(Collectors.toSet());
        entity.setRoles(roles);
        if (entity.getActivo() == null) {
            entity.setActivo(true);
        }
        return userMapper.toDto(crudUser.save(entity));
    }

    @Override
    public Optional<UserDto> update(Integer id, UserDto dto) {
        return crudUser.findById(id)
                .map(user -> {
                    user.setUsername(dto.username());
                    user.setPassword(dto.password());
                    user.setActivo(dto.activo());

                    Set<RolEntity> roles = dto.roles()
                            .stream()
                            .map(RolDto::id)
                            .map(roleId ->
                                crudRol.findById(roleId)
                                        .orElseThrow(() ->
                                                new RuntimeException("Rol no encontrado"))
                            ).collect(Collectors.toSet());
                    user.setRoles(roles);
                    return crudUser.save(user);
                }).map(userMapper::toDto);
    }

    @Override
    public boolean delete(Integer id) {
        return crudUser.findById(id)
                .map(user -> {
                    user.setDeletedAt(LocalDateTime.now());
                    crudUser.save(user);
                    return true;
                }).orElse(false);
    }

    @Override
    public boolean restore(Integer id) {
        return crudUser.findById(id)
                .map(user -> {
                    user.setDeletedAt(null);
                    crudUser.save(user);
                    return true;
                }).orElse(false);
    }

    @Override
    public UserDto login(LoginDto dto) {
        UserEntity user = crudUser
                .findByUsername(dto.getUsername())
                .orElseThrow(
                        () -> new UserNotFoundException(dto.getUsername())
                );
        if (!user.getPassword().equals(dto.getPassword())) {
            throw new InvalidPasswordException();
        }
        return userMapper.toDto(user);
    }

    @Override
    public boolean deactivate(Integer id) {
        return crudUser.findById(id)
                .map(user -> {
                    user.setActivo(false);
                    crudUser.save(user);
                    return true;
                }).orElse(false);
    }

    @Override
    public boolean activate(Integer id) {
        return crudUser.findById(id)
                .map(user -> {
                    user.setActivo(true);
                    crudUser.save(user);
                    return true;
                }).orElse(false);
    }
}
