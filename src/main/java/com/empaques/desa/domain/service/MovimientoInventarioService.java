package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.MovimientoInventarioDto;
import com.empaques.desa.domain.repository.MovimientoInventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoInventarioService {
    private final MovimientoInventarioRepository movimientoInventarioRepository;

    public MovimientoInventarioService(MovimientoInventarioRepository movimientoInventarioRepository) {
        this.movimientoInventarioRepository = movimientoInventarioRepository;
    }

    public List<MovimientoInventarioDto> getAll() {
        return movimientoInventarioRepository.getAll();
    }

    public Optional<MovimientoInventarioDto> getById(Integer id) {
        return movimientoInventarioRepository.getById(id);
    }

    public MovimientoInventarioDto save(MovimientoInventarioDto dto) {
        return movimientoInventarioRepository.save(dto);
    }

    public Optional<MovimientoInventarioDto> update(Integer id, MovimientoInventarioDto dto) {
        return movimientoInventarioRepository.update(id, dto);
    }

    public boolean delete(Integer id) {
        return movimientoInventarioRepository.delete(id);
    }
}
