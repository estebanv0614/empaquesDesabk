package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.TipoGastoDto;
import com.empaques.desa.domain.repository.TipoGastoRepository;
import com.empaques.desa.persistence.crud.CrudTipoGastoEntity;
import com.empaques.desa.persistence.mapper.TipoGastoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TipoGastoEntityRepository implements TipoGastoRepository {
    private final CrudTipoGastoEntity crudTipoGasto;
    private final TipoGastoMapper tipoGastoMapper;

    public TipoGastoEntityRepository(CrudTipoGastoEntity crudTipoGasto, TipoGastoMapper tipoGastoMapper) {
        this.crudTipoGasto = crudTipoGasto;
        this.tipoGastoMapper = tipoGastoMapper;
    }

    @Override
    public List<TipoGastoDto> getAll() {
        return tipoGastoMapper.toDtoList(crudTipoGasto.findAll());
    }

    @Override
    public Optional<TipoGastoDto> getById(Integer id) {
        return crudTipoGasto.findById(id)
                .map(tipoGastoMapper::toDto);
    }

    @Override
    public TipoGastoDto save(TipoGastoDto dto) {
        return Optional.of(dto)
                .map(tipoGastoMapper::toEntity)
                .map(crudTipoGasto::save)
                .map(tipoGastoMapper::toDto)
                .orElseThrow();
    }

    @Override
    public Optional<TipoGastoDto> update(Integer id, TipoGastoDto dto) {
        return crudTipoGasto.findById(id)
                .map(entity ->{
                    entity.setName(dto.name());
                    return entity;
                })
                .map(crudTipoGasto::save)
                .map(tipoGastoMapper::toDto);
    }

    @Override
    public boolean delete(Integer id) {
        return Optional.of(id)
                .filter(crudTipoGasto::existsById)
                .map(validId ->{
                    crudTipoGasto.deleteById(id);
                    return true;
                })
                .orElse(false);
    }
}
