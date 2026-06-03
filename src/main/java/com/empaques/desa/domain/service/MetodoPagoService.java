package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.MetodoPagoDto;
import com.empaques.desa.domain.repository.MetodoPagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetodoPagoService {
    private final MetodoPagoRepository metodoPagoRepository;

    public MetodoPagoService(MetodoPagoRepository metodoPagoRepository) {
        this.metodoPagoRepository = metodoPagoRepository;
    }

    public List<MetodoPagoDto> getAll() {
        return metodoPagoRepository.getAll();
    }

    public Optional<MetodoPagoDto> getById(Integer id) {
        return metodoPagoRepository.getById(id);
    }

    public MetodoPagoDto save(MetodoPagoDto dto) {
        return metodoPagoRepository.save(dto);
    }

    public Optional<MetodoPagoDto> update(Integer id, MetodoPagoDto dto) {
        return metodoPagoRepository.update(id, dto);
    }

    public boolean delete(Integer id) {
        return metodoPagoRepository.delete(id);
    }
}
