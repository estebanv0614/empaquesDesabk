package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.MaterialDto;
import com.empaques.desa.domain.repository.MaterialRepository;
import com.empaques.desa.persistence.crud.CrudMaterialEntity;
import com.empaques.desa.persistence.entity.MaterialEntity;
import com.empaques.desa.persistence.mapper.MaterialMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class MaterialEntityRepository implements MaterialRepository {
    private final CrudMaterialEntity crudMaterial;
    private final MaterialMapper materialMapper;

    public MaterialEntityRepository(CrudMaterialEntity crudMaterial, MaterialMapper materialMapper) {
        this.crudMaterial = crudMaterial;
        this.materialMapper = materialMapper;
    }

    @Override
    public List<MaterialDto> getAll() {
        return materialMapper.toDtoList(crudMaterial.findAll());
    }

    @Override
    public Optional<MaterialDto> getById(Integer id) {
        return crudMaterial.findById(id)
                .map(materialMapper::toDto);
    }

    @Override
    public MaterialDto save(MaterialDto dto) {
        MaterialEntity entity = materialMapper.toEntity(dto);
        return materialMapper.toDto(crudMaterial.save(entity));
    }

    @Override
    public Optional<MaterialDto> update(Integer id, MaterialDto dto) {
        return crudMaterial.findById(id)
                .map(entity -> {
                    entity.setName(dto.name());
                    entity.setUnitMeasurement(dto.unitMeasurement());
                    entity.setCostoUnitario(dto.costoUnitario());
                    entity.setStockCurrent(dto.stockCurrent());
                    return entity;
                })
                .map(crudMaterial::save)
                .map(materialMapper::toDto);
    }

    @Override
    public boolean delete(Integer id) {
        return crudMaterial.findById(id)
                .map(entity -> {
                    entity.setDeletedAt(LocalDateTime.now());
                    crudMaterial.save(entity);
                    return true;
                })
                .orElse(false);
    }
}
