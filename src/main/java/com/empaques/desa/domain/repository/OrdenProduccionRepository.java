package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.OrdenProduccionDto;

import java.util.List;
import java.util.Optional;

public interface OrdenProduccionRepository {
    List<OrdenProduccionDto> getAll();
    Optional<OrdenProduccionDto> getById(Integer id);
    OrdenProduccionDto save(OrdenProduccionDto dto);
    Optional<OrdenProduccionDto> update(Integer id, OrdenProduccionDto dto);
    boolean delete(Integer id);
}
