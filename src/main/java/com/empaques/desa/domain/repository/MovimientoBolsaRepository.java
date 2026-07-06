package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.MovimientoBolsaDto;

import java.util.List;
import java.util.Optional;

public interface MovimientoBolsaRepository {
    List<MovimientoBolsaDto> getAll();
    Optional<MovimientoBolsaDto> getById(Integer id);
    MovimientoBolsaDto save(MovimientoBolsaDto dto);
    Optional<MovimientoBolsaDto> update(Integer id, MovimientoBolsaDto dto);
    boolean delete(Integer id);
}
