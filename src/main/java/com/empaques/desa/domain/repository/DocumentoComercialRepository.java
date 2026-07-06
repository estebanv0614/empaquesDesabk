package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.DocumentoComercialDto;

import java.util.List;
import java.util.Optional;

public interface DocumentoComercialRepository {
    List<DocumentoComercialDto> getAll();
    Optional<DocumentoComercialDto> getById(Integer id);
    DocumentoComercialDto save(DocumentoComercialDto dto);
    Optional<DocumentoComercialDto> update(Integer id, DocumentoComercialDto dto);
    boolean delete(Integer id);
}
