package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.MovimientoInventarioDto;

import java.util.List;
import java.util.Optional;

public interface MovimientoInventarioRepository {
    List<MovimientoInventarioDto> getAll();
    Optional<MovimientoInventarioDto> getById(Integer id);
    MovimientoInventarioDto save(MovimientoInventarioDto dto);
    Optional<MovimientoInventarioDto> update(Integer id, MovimientoInventarioDto dto);
    boolean delete(Integer id);

}
