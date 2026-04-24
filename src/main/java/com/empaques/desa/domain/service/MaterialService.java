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
        return this.materialRepository.getAll();
    }

    public Optional<MaterialDto> getById(Integer id) {
        return this.materialRepository.getById(id);
    }

    public MaterialDto save(MaterialDto dto) {
        return this.materialRepository.save(dto);
    }

    public Optional<MaterialDto> update(Integer id, MaterialDto dto) {
        return this.materialRepository.update(id, dto);
    }

    public void delete(Integer id) {
        materialRepository.delete(id);
    }
}
