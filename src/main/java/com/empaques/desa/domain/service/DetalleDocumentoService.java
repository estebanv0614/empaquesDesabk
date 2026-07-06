package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.DetalleDocumentoDto;
import com.empaques.desa.domain.repository.DetalleDocumentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleDocumentoService {
    private final DetalleDocumentoRepository detalleDocumentoRepository;

    public DetalleDocumentoService(DetalleDocumentoRepository detalleDocumentoRepository) {
        this.detalleDocumentoRepository = detalleDocumentoRepository;
    }

    public List<DetalleDocumentoDto> getAll() {
        return detalleDocumentoRepository.getAll();
    }

    public Optional<DetalleDocumentoDto> getById(Integer id) {
        return detalleDocumentoRepository.getById(id);
    }

    public DetalleDocumentoDto save(DetalleDocumentoDto dto) {
        return detalleDocumentoRepository.save(dto);
    }

    public Optional<DetalleDocumentoDto> update(Integer id, DetalleDocumentoDto dto) {
        return detalleDocumentoRepository.update(id, dto);
    }

    public boolean delete(Integer id) {
        return detalleDocumentoRepository.delete(id);
    }
}
