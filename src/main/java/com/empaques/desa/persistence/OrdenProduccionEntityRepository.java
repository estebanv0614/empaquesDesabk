package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.OrdenProduccionDto;
import com.empaques.desa.domain.repository.OrdenProduccionRepository;
import com.empaques.desa.persistence.crud.CrudBolsaEntity;
import com.empaques.desa.persistence.crud.CrudEstadoEntity;
import com.empaques.desa.persistence.crud.CrudOrdenProduccionEntity;
import com.empaques.desa.persistence.entity.OrdenProduccionEntity;
import com.empaques.desa.persistence.mapper.OrdenProduccionMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrdenProduccionEntityRepository implements OrdenProduccionRepository {
    private final CrudOrdenProduccionEntity ordenProduccion;
    private final CrudBolsaEntity bolsa;
    private final CrudEstadoEntity estadoEntity;
    private final OrdenProduccionMapper produccionMapper;

    public OrdenProduccionEntityRepository(CrudOrdenProduccionEntity ordenProduccion, CrudBolsaEntity bolsa, CrudEstadoEntity estadoEntity, OrdenProduccionMapper produccionMapper) {
        this.ordenProduccion = ordenProduccion;
        this.bolsa = bolsa;
        this.estadoEntity = estadoEntity;
        this.produccionMapper = produccionMapper;
    }

    @Override
    public List<OrdenProduccionDto> getAll() {
        return produccionMapper.toDtoList(ordenProduccion.findAll());
    }

    @Override
    public Optional<OrdenProduccionDto> getById(Integer id) {
        return ordenProduccion.findById(id)
                .map(produccionMapper::toDto);
    }

    @Override
    public OrdenProduccionDto save(OrdenProduccionDto dto) {
        OrdenProduccionEntity entity = produccionMapper.toEntity(dto);
        entity.setBolsa(
                bolsa.findById(dto.bolsa().id())
                        .orElseThrow(() -> new RuntimeException("No existe la bolsa " + dto.bolsa().id() + " not found"))
        );
        entity.setEstado(
                estadoEntity.findById(dto.estado().id())
                        .orElseThrow(() -> new RuntimeException("Estado no esta disponible " + dto.estado().id()))
        );
        return produccionMapper.toDto(ordenProduccion.save(entity));
    }

    @Override
    public Optional<OrdenProduccionDto> update(Integer id, OrdenProduccionDto dto) {
        return ordenProduccion.findById(id)
                .map(entity -> {
                    entity.setCantidadProducir(dto.cantidadProducir());
                    entity.setFechaProduccion(dto.fechaProduccion());
                    entity.setBolsa(
                            bolsa.findById(dto.bolsa().id())
                                    .orElseThrow(() -> new RuntimeException("No existe la bolsa " + dto.bolsa().id() + " not found"))
                    );
                    entity.setEstado(
                            estadoEntity.findById(dto.estado().id())
                                    .orElseThrow(() -> new RuntimeException("Estado no esta disponible " + dto.estado().id()))
                    );
                    OrdenProduccionEntity updated = ordenProduccion.save(entity);
                    return produccionMapper.toDto(updated);
                });
    }

    @Override
    public boolean delete(Integer id) {
        return ordenProduccion.findById(id)
                .map(entity -> {
                    ordenProduccion.save(entity);
                    return true;
                }).orElse(false);
    }
}
