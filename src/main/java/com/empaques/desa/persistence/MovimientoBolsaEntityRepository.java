package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.MovimientoBolsaDto;
import com.empaques.desa.domain.repository.MovimientoBolsaRepository;
import com.empaques.desa.persistence.crud.CrudMaterialEntity;
import com.empaques.desa.persistence.crud.CrudMovimientoBolsaEntity;
import com.empaques.desa.persistence.crud.CrudOrdenProduccionEntity;
import com.empaques.desa.persistence.crud.CrudUserEntity;
import com.empaques.desa.persistence.entity.MovimientoBolsaEntity;
import com.empaques.desa.persistence.mapper.MovimientoBolsaMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class MovimientoBolsaEntityRepository implements MovimientoBolsaRepository {
    private final CrudMovimientoBolsaEntity movimientoBolsa;
    private final CrudMaterialEntity material;
    private final CrudOrdenProduccionEntity ordenProduccion;
    private final CrudUserEntity user;
    private final MovimientoBolsaMapper bolsaMapper;

    public MovimientoBolsaEntityRepository(CrudMovimientoBolsaEntity movimientoBolsa, CrudMaterialEntity material, CrudOrdenProduccionEntity ordenProduccion, CrudUserEntity user, MovimientoBolsaMapper bolsaMapper) {
        this.movimientoBolsa = movimientoBolsa;
        this.material = material;
        this.ordenProduccion = ordenProduccion;
        this.user = user;
        this.bolsaMapper = bolsaMapper;
    }

    @Override
    public List<MovimientoBolsaDto> getAll() {
        return bolsaMapper.toDtoList(movimientoBolsa.findAll());
    }

    @Override
    public Optional<MovimientoBolsaDto> getById(Integer id) {
        return movimientoBolsa.findById(id)
                .map(bolsaMapper::toDto);
    }

    @Override
    public MovimientoBolsaDto save(MovimientoBolsaDto dto) {
        MovimientoBolsaEntity entity = bolsaMapper.toEntity(dto);
        entity.setMaterial(
                material.findById(dto.material().id())
                        .orElseThrow(() -> new RuntimeException("Material no encontrado"))
        );
        if (dto.ordenProduccion() != null) {
            entity.setOrdenProduccion(
                    ordenProduccion.findById(dto.ordenProduccion().id())
                            .orElseThrow(() -> new RuntimeException("Orden Produccion no encontrada"))
            );
        }
        entity.setUser(
                user.findById(dto.user().id())
                        .orElseThrow(() -> new RuntimeException("User no encontrado"))
        );
        return bolsaMapper.toDto(movimientoBolsa.save(entity));
    }

    @Override
    public Optional<MovimientoBolsaDto> update(Integer id, MovimientoBolsaDto dto) {
        return movimientoBolsa.findById(id)
                .map(entity -> {
                    entity.setMovimientoBolsa(dto.movimientoBolsa());
                    entity.setCantidad(dto.cantidad());
                    entity.setStockAntes(dto.stockAntes());
                    entity.setStockDespues(dto.stockDespues());
                    entity.setFechaMovimiento(dto.fechaMovimiento());
                    entity.setReferencia(dto.referencia());
                    entity.setObservacion(dto.observacion());
                    MovimientoBolsaEntity updated = movimientoBolsa.save(entity);
                    return bolsaMapper.toDto(updated);
                });
    }

    @Override
    public boolean delete(Integer id) {
        return movimientoBolsa.findById(id)
                .map(entity -> {
                    entity.setDeletedAt(LocalDateTime.now());
                    movimientoBolsa.save(entity);
                    return true;
                }).orElse(false);
    }
}
