package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.MetodoPagoDto;
import com.empaques.desa.domain.repository.MetodoPagoRepository;
import com.empaques.desa.persistence.crud.CrudEstadoEntity;
import com.empaques.desa.persistence.crud.CrudMetodoPagoEntity;
import com.empaques.desa.persistence.mapper.MetodoPagoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MetodoPagoEntityRepository implements MetodoPagoRepository {
    private final MetodoPagoMapper metodoPagoMapper;
    private final CrudMetodoPagoEntity crudMetodoPago;

    public MetodoPagoEntityRepository(MetodoPagoMapper metodoPagoMapper, CrudMetodoPagoEntity crudMetodoPago) {
        this.metodoPagoMapper = metodoPagoMapper;
        this.crudMetodoPago = crudMetodoPago;
    }

    @Override
    public List<MetodoPagoDto> getAll() {
        return metodoPagoMapper.toDtoList(crudMetodoPago.findAll());
    }

    @Override
    public Optional<MetodoPagoDto> getById(Integer id) {
        return crudMetodoPago.findById(id)
                .map(metodoPagoMapper::toDto);
    }

    @Override
    public MetodoPagoDto save(MetodoPagoDto dto) {
        return Optional.of(dto)
                .map(metodoPagoMapper::toEntity)
                .map(crudMetodoPago::save)
                .map(metodoPagoMapper::toDto)
                .orElseThrow();
    }

    @Override
    public Optional<MetodoPagoDto> update(Integer id, MetodoPagoDto dto) {
        return crudMetodoPago.findById(id)
                .map(entity -> {
                    entity.setName(dto.name());
                    return entity;
                })
                .map(crudMetodoPago::save)
                .map(metodoPagoMapper::toDto);
    }

    @Override
    public boolean delete(Integer id) {
        return Optional.of(id)
                .filter(crudMetodoPago::existsById)
                .map(validId -> {
                    crudMetodoPago.findById(validId);
                    return true;
                })
                .orElse(false);
    }
}
