package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.MaterialDto;
import com.empaques.desa.domain.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }
    public List<MaterialDto> getAll() {
        return materialRepository.getAll();
    }

    public Optional<MaterialDto> getById(Integer id) {
        return materialRepository.getById(id);
    }

    public MaterialDto save(MaterialDto dto) {
        return materialRepository.save(dto);
    }

    public Optional<MaterialDto> update(Integer id, MaterialDto dto) {
        return materialRepository.update(id, dto);
    }

    public boolean delete(Integer id) {
        return materialRepository.delete(id);
    }
}
