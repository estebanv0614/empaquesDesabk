package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.EstadoDto;
import com.empaques.desa.domain.repository.EstadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoService {
    private final EstadoRepository estadoRepository;

    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public List<EstadoDto> getAll() {
        return estadoRepository.getAll();
    }

    public Optional<EstadoDto> getById(Integer id) {
        return estadoRepository.getById(id);
    }

    public EstadoDto save(EstadoDto dto){
        return estadoRepository.save(dto);
    }

    public Optional<EstadoDto> update(Integer id, EstadoDto dto) {
        return estadoRepository.update(id, dto);
    }

    public boolean delete(Integer id) {
        return estadoRepository.delete(id);
    }
}
