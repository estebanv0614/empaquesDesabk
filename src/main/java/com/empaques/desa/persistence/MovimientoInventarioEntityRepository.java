package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.MovimientoInventarioDto;
import com.empaques.desa.domain.repository.MovimientoInventarioRepository;
import com.empaques.desa.persistence.crud.CrudMaterialEntity;
import com.empaques.desa.persistence.crud.CrudMovimientoInventarioEntity;
import com.empaques.desa.persistence.crud.CrudOrdenProduccionEntity;
import com.empaques.desa.persistence.crud.CrudUserEntity;
import com.empaques.desa.persistence.entity.MovimientoInventarioEntity;
import com.empaques.desa.persistence.mapper.MovimientoInventarioMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class MovimientoInventarioEntityRepository implements MovimientoInventarioRepository {

    private final CrudMovimientoInventarioEntity crudMovimiento;
    private final CrudMaterialEntity crudMaterial;
    private final CrudOrdenProduccionEntity crudOrdenProduccion;
    private final CrudUserEntity crudUser;
    private final MovimientoInventarioMapper movimientoMapper;

    public MovimientoInventarioEntityRepository(CrudMovimientoInventarioEntity crudMovimiento, CrudMaterialEntity crudMaterial, CrudOrdenProduccionEntity crudOrdenProduccion, CrudUserEntity crudUser, MovimientoInventarioMapper movimientoMapper) {
        this.crudMovimiento = crudMovimiento;
        this.crudMaterial = crudMaterial;
        this.crudOrdenProduccion = crudOrdenProduccion;
        this.crudUser = crudUser;
        this.movimientoMapper = movimientoMapper;
    }

    @Override
    public List<MovimientoInventarioDto> getAll() {
        return movimientoMapper.toDtoList(crudMovimiento.findAll());
    }

    @Override
    public Optional<MovimientoInventarioDto> getById(Integer id) {
        return crudMovimiento.findById(id)
                .map(movimientoMapper::toDto);
    }

    @Override
    public MovimientoInventarioDto save(MovimientoInventarioDto dto) {
        MovimientoInventarioEntity entity = movimientoMapper.toEntity(dto);
        entity.setMaterial(
                crudMaterial.findById(dto.material().id())
                        .orElseThrow(() -> new RuntimeException(
                                "Material no encontrado"))
        );
        if (dto.ordenProduccion() != null) {
            entity.setOrdenProduccion(
                    crudOrdenProduccion.findById(dto.ordenProduccion().id())
                            .orElseThrow(() -> new RuntimeException(
                                    "Orden no encontrada"))
            );
        }
        entity.setUser(
                crudUser.findById(dto.user().id())
                        .orElseThrow(() -> new RuntimeException(
                                "Usuario no encontrado"))
        );
        return movimientoMapper.toDto(crudMovimiento.save(entity));
    }

    @Override
    public Optional<MovimientoInventarioDto> update(Integer id, MovimientoInventarioDto dto) {
        return crudMovimiento.findById(id)
                .map(entity -> {
                    entity.setTipoMovimiento(dto.tipoMovimiento());
                    entity.setCantidad(dto.cantidad());
                    entity.setStockAntes(dto.stockAntes());
                    entity.setStockDespues(dto.stockDespues());
                    entity.setFechaMovimiento(dto.fechaMovimiento());
                    entity.setReferencia(dto.referencia());
                    entity.setObservacion(dto.observacion());
                    MovimientoInventarioEntity updated = crudMovimiento.save(entity);
                    return movimientoMapper.toDto(updated);
                });
    }

    @Override
    public boolean delete(Integer id) {
        return crudMovimiento.findById(id)
                .map(entity -> {
                    entity.setDeletedAt(LocalDateTime.now());
                    crudMovimiento.save(entity);
                    return true;
                }).orElse(false);
    }
}
