package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.MetodoPagoDto;

import java.util.List;
import java.util.Optional;

public interface MetodoPagoRepository {
    List<MetodoPagoDto> getAll();
    Optional<MetodoPagoDto> getById(Integer id);
    MetodoPagoDto save(MetodoPagoDto dto);
    Optional<MetodoPagoDto> update(Integer id, MetodoPagoDto dto);
    boolean delete(Integer id);
}
