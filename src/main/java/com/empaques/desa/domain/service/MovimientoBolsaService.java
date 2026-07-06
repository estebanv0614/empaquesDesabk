package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.MovimientoBolsaDto;
import com.empaques.desa.domain.repository.MovimientoBolsaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoBolsaService {
    private final MovimientoBolsaRepository movimientoBolsaRepository;

    public MovimientoBolsaService(MovimientoBolsaRepository movimientoBolsaRepository) {
        this.movimientoBolsaRepository = movimientoBolsaRepository;
    }

    public List<MovimientoBolsaDto> getAll() {
        return movimientoBolsaRepository.getAll();
    }

    public Optional<MovimientoBolsaDto> getById(Integer id) {
        return movimientoBolsaRepository.getById(id);
    }

    public MovimientoBolsaDto save(MovimientoBolsaDto dto) {
        return movimientoBolsaRepository.save(dto);
    }

    public Optional<MovimientoBolsaDto> update(Integer id, MovimientoBolsaDto dto) {
        return movimientoBolsaRepository.update(id, dto);
    }

    public boolean delete(Integer id) {
        return movimientoBolsaRepository.delete(id);
    }
}
