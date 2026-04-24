package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.StateDto;

import java.util.List;
import java.util.Optional;

public interface StateRepository {
    List<StateDto> getAll();
    Optional<StateDto> getById(Integer id);
    StateDto save(StateDto dto);
    Optional<StateDto> update(Integer id, StateDto dto);
    boolean delete(Integer id);
}
