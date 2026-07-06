package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.OrdenProduccionDto;
import com.empaques.desa.domain.repository.OrdenProduccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenProduccionService {
    private final OrdenProduccionRepository produccionRepository;

    public OrdenProduccionService(OrdenProduccionRepository produccionRepository) {
        this.produccionRepository = produccionRepository;
    }

    public List<OrdenProduccionDto> getAll() {
        return produccionRepository.getAll();
    }

    public Optional<OrdenProduccionDto> getById(Integer id) {
        return produccionRepository.getById(id);
    }

    public OrdenProduccionDto save(OrdenProduccionDto dto) {
        return produccionRepository.save(dto);
    }

    public Optional<OrdenProduccionDto> update(Integer id, OrdenProduccionDto dto) {
        return produccionRepository.update(id, dto);
    }

    public boolean delete(Integer id) {
        return produccionRepository.delete(id);
    }
}
