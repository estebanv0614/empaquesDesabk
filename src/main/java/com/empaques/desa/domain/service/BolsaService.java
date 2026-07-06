package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.BolsaDto;
import com.empaques.desa.domain.repository.BolsaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BolsaService {
    private final BolsaRepository bolsaRepository;

    public BolsaService(BolsaRepository bolsaRepository) {
        this.bolsaRepository = bolsaRepository;
    }

    public List<BolsaDto> getAll() {
        return bolsaRepository.getAll();
    }

    public Optional<BolsaDto> getById(Integer id) {
        return bolsaRepository.getById(id);
    }

    public BolsaDto save(BolsaDto dto) {
        return bolsaRepository.save(dto);
    }

    public Optional<BolsaDto> update(Integer id, BolsaDto dto) {
        return bolsaRepository.update(id, dto);
    }

    public boolean delete(Integer id) {
        return bolsaRepository.delete(id);
    }
}
