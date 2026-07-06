package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.DocumentoComercialDto;
import com.empaques.desa.domain.repository.DocumentoComercialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentoComercialService {
    private final DocumentoComercialRepository documentoComercial;

    public DocumentoComercialService(DocumentoComercialRepository documentoComercial) {
        this.documentoComercial = documentoComercial;
    }

    public List<DocumentoComercialDto> getAll() {
        return documentoComercial.getAll();
    }

    public Optional<DocumentoComercialDto> getById(Integer id) {
        return documentoComercial.getById(id);
    }

    public DocumentoComercialDto save(DocumentoComercialDto dto) {
        return documentoComercial.save(dto);
    }

    public Optional<DocumentoComercialDto> update(Integer id, DocumentoComercialDto dto) {
        return documentoComercial.update(id, dto);
    }

    public boolean delete(Integer id) {
        return documentoComercial.delete(id);
    }
}
