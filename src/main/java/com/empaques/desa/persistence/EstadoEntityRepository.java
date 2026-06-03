package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.EstadoDto;
import com.empaques.desa.domain.repository.EstadoRepository;
import com.empaques.desa.persistence.crud.CrudEstadoEntity;
import com.empaques.desa.persistence.mapper.EstadoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EstadoEntityRepository implements EstadoRepository {
    private final CrudEstadoEntity crudEstado;
    private final EstadoMapper estadoMapper;

    public EstadoEntityRepository(CrudEstadoEntity crudEstado, EstadoMapper estadoMapper) {
        this.crudEstado = crudEstado;
        this.estadoMapper = estadoMapper;
    }

    @Override
    public List<EstadoDto> getAll() {
        return estadoMapper.toDtoList(crudEstado.findAll());
    }

    @Override
    public Optional<EstadoDto> getById(Integer id) {
        return crudEstado.findById(id)
                .map(estadoMapper::toDto);
    }

    @Override
    public EstadoDto save(EstadoDto dto) {
        return Optional.of(dto)
                .map(estadoMapper::toEntity)
                .map(crudEstado::save)
                .map(estadoMapper::toDto)
                .orElseThrow();
    }

    @Override
    public Optional<EstadoDto> update(Integer id, EstadoDto dto) {
        return crudEstado.findById(id)
                .map(entity -> {
                    entity.setName(dto.name());
                    return entity;
                })
                .map(crudEstado::save)
                .map(estadoMapper::toDto);
    }

    @Override
    public boolean delete(Integer id) {
        return Optional.of(id)
                .filter(crudEstado::existsById)
                .map(validId -> {
                    crudEstado.deleteById(validId);
                    return true;
                }).orElse(false);
    }
}
