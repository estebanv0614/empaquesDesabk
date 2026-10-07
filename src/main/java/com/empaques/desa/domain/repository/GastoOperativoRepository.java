package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.GastoOperativoDto;
import com.empaques.desa.domain.dto.GastoOperativoRequest;

import java.util.List;
import java.util.Optional;

public interface GastoOperativoRepository {
    List<GastoOperativoDto> getAll();
    Optional<GastoOperativoDto> getById(Integer id);
    GastoOperativoDto save(GastoOperativoRequest request, String evidenciaUrl);
    boolean delete(Integer id);
}
