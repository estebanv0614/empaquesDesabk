package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.UserDto;
import com.empaques.desa.domain.repository.UserRepository;
import com.empaques.desa.persistence.crud.CrudUserEntity;
import com.empaques.desa.persistence.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserEntityRepository implements UserRepository {
    private final CrudUserEntity crudUserEntity;
    private final UserMapper userMapper;

    public UserEntityRepository(CrudUserEntity crudUserEntity, UserMapper userMapper) {
        this.crudUserEntity = crudUserEntity;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> getAll() {
        return userMapper.toDtoList(crudUserEntity.findAll());
    }

    @Override
    public Optional<UserDto> getById(Integer id) {
        return crudUserEntity.findById(id)
                .map(userMapper::toDto);
    }

    @Override
    public UserDto seva(UserDto dto) {
        return Optional.of(dto)
                .map(userMapper::toEntity)
                .map(crudUserEntity::save)
                .map(userMapper::toDto)
                .orElseThrow();
    }

    @Override
    public Optional<UserDto> update(Integer id, UserDto dto) {
        return crudUserEntity.findById(id)
                .map(userEntity -> {
                    userEntity.setUsername(dto.username());
                    userEntity.setPassword(dto.password());
                    userEntity.setActivo(dto.activo());
                    return userEntity;
                })
                .map(crudUserEntity::save)
                .map(userMapper::toDto);
    }

    @Override
    public boolean delete(Integer id) {
        return crudUserEntity.findById(id)
                .map(entity -> {
                    entity.setActivo(false);
                    crudUserEntity.save(entity);
                    return true;
                })
                .orElse(false);
    }
}
