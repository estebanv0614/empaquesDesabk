package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.MaterialDto;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository {
    List<MaterialDto> getAll();
    Optional<MaterialDto> getById(Integer id);
    MaterialDto save(MaterialDto dto);
    Optional<MaterialDto> update(Integer id, MaterialDto dto);
    boolean delete(Integer id);
}
