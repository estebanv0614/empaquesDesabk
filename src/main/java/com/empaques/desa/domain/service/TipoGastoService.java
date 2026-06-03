package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.TipoGastoDto;
import com.empaques.desa.domain.repository.TipoGastoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoGastoService {
    private final TipoGastoRepository tipoGastoRepository;

    public TipoGastoService(TipoGastoRepository tipoGastoRepository) {
        this.tipoGastoRepository = tipoGastoRepository;
    }

    public List<TipoGastoDto> getAll() {
        return tipoGastoRepository.getAll();
    }

    public Optional<TipoGastoDto> getById(Integer id) {
        return tipoGastoRepository.getById(id);
    }

    public TipoGastoDto save(TipoGastoDto dto) {
        return tipoGastoRepository.save(dto);
    }

    public Optional<TipoGastoDto> update(Integer id, TipoGastoDto dto) {
        return tipoGastoRepository.update(id, dto);
    }

    public boolean delete(Integer id) {
        return tipoGastoRepository.delete(id);
    }
}
