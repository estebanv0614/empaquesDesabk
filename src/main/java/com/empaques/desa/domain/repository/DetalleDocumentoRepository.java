package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.DetalleDocumentoDto;

import java.util.List;
import java.util.Optional;

public interface DetalleDocumentoRepository {
    List<DetalleDocumentoDto> getAll();
    Optional<DetalleDocumentoDto> getById(Integer id);
    DetalleDocumentoDto save(DetalleDocumentoDto dto);
    Optional<DetalleDocumentoDto> update(Integer id, DetalleDocumentoDto dto);
    boolean delete(Integer id);
}
