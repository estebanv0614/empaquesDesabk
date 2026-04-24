package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.StateDto;
import com.empaques.desa.domain.repository.StateRepository;
import com.empaques.desa.persistence.crud.CrudStateEntity;
import com.empaques.desa.persistence.mapper.StateMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StateEntityRepository implements StateRepository {

    private final CrudStateEntity crudStateEntity;
    private final StateMapper stateMapper;

    public StateEntityRepository(CrudStateEntity crudStateEntity, StateMapper stateMapper) {
        this.crudStateEntity = crudStateEntity;
        this.stateMapper = stateMapper;
    }

    @Override
    public List<StateDto> getAll() {
        return stateMapper.toDtoList(crudStateEntity.findAll());
    }

    @Override
    public Optional<StateDto> getById(Integer id) {
        return crudStateEntity.findById(id)
                .map(stateMapper::toDto);
    }

    @Override
    public StateDto save(StateDto dto) {
        return Optional.of(dto)
                .map(stateMapper::toEntity)
                .map(crudStateEntity::save)
                .map(stateMapper::toDto)
                .orElseThrow();
    }

    @Override
    public Optional<StateDto> update(Integer id, StateDto dto) {
        return crudStateEntity.findById(id)
                .map(entity -> {
                    entity.setName(dto.name());
                    return entity;
                })
                .map(crudStateEntity::save)
                .map(stateMapper::toDto);
    }

    @Override
    public boolean delete(Integer id) {
        return Optional.of(id)
                .filter(crudStateEntity::existsById)
                .map(validId -> {
                    crudStateEntity.deleteById(validId);
                    return true;
                })
                .orElse(false);
    }
}
