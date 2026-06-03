package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.TipoGastoDto;

import java.util.List;
import java.util.Optional;

public interface TipoGastoRepository {
    List<TipoGastoDto> getAll();
    Optional<TipoGastoDto> getById(Integer id);
    TipoGastoDto save(TipoGastoDto dto);
    Optional<TipoGastoDto> update(Integer id, TipoGastoDto dto);
    boolean delete(Integer id);
}
