package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.EstadoDto;

import java.util.List;
import java.util.Optional;

public interface EstadoRepository {
    List<EstadoDto> getAll();
    Optional<EstadoDto> getById(Integer id);
    EstadoDto save(EstadoDto dto);
    Optional<EstadoDto> update(Integer id, EstadoDto dto);
    boolean delete(Integer id);
}
