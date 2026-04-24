package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.BolsaDto;

import java.util.List;
import java.util.Optional;

public interface BolsaRepository {
    List<BolsaDto> getAll();
    Optional<BolsaDto> getById(Integer id);
    BolsaDto save(BolsaDto dto);
    Optional<BolsaDto> update(Integer id, BolsaDto dto);
    void delete(Integer id);
}
