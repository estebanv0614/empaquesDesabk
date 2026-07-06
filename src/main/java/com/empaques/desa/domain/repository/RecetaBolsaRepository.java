package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.RecetaBolsaDto;

import java.util.List;
import java.util.Optional;

public interface RecetaBolsaRepository {
    List<RecetaBolsaDto> getAll();
    Optional<RecetaBolsaDto> getById(Integer idBolsa, Integer idMaterial);
    RecetaBolsaDto save(RecetaBolsaDto dto);
    Optional<RecetaBolsaDto> update(Integer idBolsa, Integer idMaterial, RecetaBolsaDto dto);
    boolean delete(Integer idBolsa, Integer idMaterial);
}
