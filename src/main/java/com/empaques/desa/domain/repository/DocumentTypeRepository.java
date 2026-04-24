package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.DocumentTypeDto;

import java.util.List;
import java.util.Optional;

public interface DocumentTypeRepository {
    List<DocumentTypeDto> getAll();
    Optional<DocumentTypeDto> getById(Integer id);

}
