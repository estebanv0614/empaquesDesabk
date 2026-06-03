package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.TipoDocumentoDto;
import com.empaques.desa.domain.repository.TipoDocumentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoDocumentoService {
    private final TipoDocumentoRepository documentTypeRepository;

    public TipoDocumentoService(TipoDocumentoRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }

    public List<TipoDocumentoDto> getAll() {
        return documentTypeRepository.getAll();
    }

    public Optional<TipoDocumentoDto> getById(Integer id) {
        return documentTypeRepository.getById(id);
    }

    public TipoDocumentoDto save(TipoDocumentoDto dto){
        return documentTypeRepository.save(dto);
    }

    public Optional<TipoDocumentoDto> update(Integer id, TipoDocumentoDto dto) {
        return documentTypeRepository.update(id, dto);
    }

    public boolean delete(Integer id) {
        return documentTypeRepository.delete(id);
    }
}
