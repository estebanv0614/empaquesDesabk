package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.RecetaBolsaDto;
import com.empaques.desa.domain.repository.RecetaBolsaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaBolsaService {
    private final RecetaBolsaRepository bolsaRepository;

    public RecetaBolsaService(RecetaBolsaRepository bolsaRepository) {
        this.bolsaRepository = bolsaRepository;
    }

    public List<RecetaBolsaDto> getAll() {
        return bolsaRepository.getAll();
    }

    public Optional<RecetaBolsaDto> getById(Integer idBolsa, Integer idMaterial) {
        return bolsaRepository.getById(idBolsa, idMaterial);
    }

    public RecetaBolsaDto save(RecetaBolsaDto dto) {
        return bolsaRepository.save(dto);
    }

    public Optional<RecetaBolsaDto> update(Integer idBolsa, Integer idMaterial, RecetaBolsaDto dto) {
        return bolsaRepository.update(idBolsa, idMaterial, dto);
    }

    public boolean delete(Integer idBolsa, Integer idMaterial) {
        return bolsaRepository.delete(idBolsa, idMaterial);
    }
}
