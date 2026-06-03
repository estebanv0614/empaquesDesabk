package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.TipoDocumentoDto;

import java.util.List;
import java.util.Optional;

public interface TipoDocumentoRepository {
    List<TipoDocumentoDto> getAll();
    Optional<TipoDocumentoDto> getById(Integer id);
    TipoDocumentoDto save(TipoDocumentoDto dto);
    Optional<TipoDocumentoDto> update(Integer id, TipoDocumentoDto dto);
    boolean delete(Integer id);

}
