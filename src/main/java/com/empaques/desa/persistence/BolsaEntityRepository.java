package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.BolsaDto;
import com.empaques.desa.domain.repository.BolsaRepository;
import com.empaques.desa.persistence.crud.CrudBolsaEntity;
import com.empaques.desa.persistence.crud.CrudEstadoEntity;
import com.empaques.desa.persistence.entity.BolsaEntity;
import com.empaques.desa.persistence.mapper.BolsaMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class BolsaEntityRepository implements BolsaRepository {
    private final CrudBolsaEntity crudBolsa;
    private final CrudEstadoEntity crudEstado;
    private final BolsaMapper bolsaMapper;

    public BolsaEntityRepository(CrudBolsaEntity crudBolsa, CrudEstadoEntity crudEstado, BolsaMapper bolsaMapper) {
        this.crudBolsa = crudBolsa;
        this.crudEstado = crudEstado;
        this.bolsaMapper = bolsaMapper;
    }

    @Override
    public List<BolsaDto> getAll() {
        return bolsaMapper.toDtoList(crudBolsa.findAll());
    }

    @Override
    public Optional<BolsaDto> getById(Integer id) {
        return crudBolsa.findById(id)
                .map(bolsaMapper::toDto);
    }

    @Override
    public BolsaDto save(BolsaDto dto) {
        BolsaEntity entity = bolsaMapper.toEntity(dto);
        if (entity.getStockActual() == null) {
            entity.setStockActual(BigDecimal.ZERO);
        }
        entity.setEstado(
                crudEstado.findById(dto.estado().id())
                        .orElseThrow(() -> new RuntimeException("Estado no esta disponible " + dto.estado().id()))
        );
        return bolsaMapper.toDto(crudBolsa.save(entity));
    }

    @Override
    public Optional<BolsaDto> update(Integer id, BolsaDto dto) {
        return crudBolsa.findById(id)
                .map(entity -> {
                    entity.setTipo(dto.tipo());
                    entity.setAnchoCm(dto.anchoCm());
                    entity.setLargoCm(dto.largoCm());
                    entity.setCalibre(dto.calibre());
                    entity.setPrecioBase(dto.precioBase());
                    if (dto.stockActual() != null) {
                        entity.setStockActual(dto.stockActual());
                    }
                    entity.setEstado(
                            crudEstado.findById(dto.estado().id())
                                    .orElseThrow(() -> new RuntimeException("Estado no esta disponible " + dto.estado().id()))
                    );
                    BolsaEntity updated = crudBolsa.save(entity);
                    return bolsaMapper.toDto(updated);
                });
    }

    @Override
    public boolean delete(Integer id) {
        return crudBolsa.findById(id)
                .map(entity -> {
                    entity.setDeletedAt(LocalDateTime.now());
                    crudBolsa.save(entity);
                    return true;
                }).orElse(false);
    }
}
