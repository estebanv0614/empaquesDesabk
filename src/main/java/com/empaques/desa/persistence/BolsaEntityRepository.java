package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.BolsaDto;
import com.empaques.desa.domain.repository.BolsaRepository;
import com.empaques.desa.persistence.crud.CrudBolsaEntity;
import com.empaques.desa.persistence.crud.CrudStateEntity;
import com.empaques.desa.persistence.entity.BolsaEntity;
import com.empaques.desa.persistence.entity.StateEntity;
import com.empaques.desa.persistence.mapper.BolsaMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class BolsaEntityRepository implements BolsaRepository {
    private final CrudBolsaEntity crudBolsa;
    private final CrudStateEntity crudState;
    private final BolsaMapper bolsaMapper;

    public BolsaEntityRepository(CrudBolsaEntity crudBolsaEntity, CrudStateEntity crudStateEntity, BolsaMapper bolsaMapper) {
        this.crudBolsa = crudBolsaEntity;
        this.crudState = crudStateEntity;
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

        entity.setState(
                crudState.findById(dto.state().id())
                        .orElseThrow(() -> new RuntimeException("state not found"))
        );
        return bolsaMapper.toDto(crudBolsa.save(entity));
    }

    @Override
    public Optional<BolsaDto> update(Integer id, BolsaDto dto) {
        return crudBolsa.findById(id)
                .map(entity -> {
                    entity.setTipoBolsa(dto.tipoBolsa());
                    entity.setAnchoCm(dto.anchoCm());
                    entity.setLargoCm(dto.largoCm());
                    entity.setCaliber(dto.caliber());
                    entity.setPriceBase(dto.priceBase());

                    if (dto.state() != null) {
                        entity.setState(
                                crudState.findById(dto.state().id())
                                        .orElseThrow()
                        );
                    }
                    return entity;
                })
                .map(crudBolsa::save)
                .map(bolsaMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        BolsaEntity entity = crudBolsa.findById(id)
                .orElseThrow(() -> new RuntimeException("Bolsa no existe"));

        StateEntity inactive = crudState.findByName("INACTIVO")
                .orElseThrow(() -> new RuntimeException("Estado INACTIVO no existe"));
        entity.setState(inactive);
        entity.setDeletedAt(LocalDateTime.now());

        crudBolsa.save(entity);
    }
}
