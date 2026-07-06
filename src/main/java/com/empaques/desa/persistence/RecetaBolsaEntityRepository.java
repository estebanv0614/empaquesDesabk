package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.RecetaBolsaDto;
import com.empaques.desa.domain.repository.RecetaBolsaRepository;
import com.empaques.desa.persistence.crud.CrudBolsaEntity;
import com.empaques.desa.persistence.crud.CrudMaterialEntity;
import com.empaques.desa.persistence.crud.CrudRecetaBolsaEntity;
import com.empaques.desa.persistence.entity.RecetaBolsaEntity;
import com.empaques.desa.persistence.entity.RecetaBolsaPK;
import com.empaques.desa.persistence.mapper.RecetaBolsaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RecetaBolsaEntityRepository implements RecetaBolsaRepository {

    private final CrudRecetaBolsaEntity recetaBolsa;
    private final CrudBolsaEntity crudBolsa;
    private final CrudMaterialEntity crudMaterial;
    private final RecetaBolsaMapper mapper;

    public RecetaBolsaEntityRepository(CrudRecetaBolsaEntity recetaBolsa, CrudBolsaEntity crudBolsa, CrudMaterialEntity crudMaterial, RecetaBolsaMapper mapper) {
        this.recetaBolsa = recetaBolsa;
        this.crudBolsa = crudBolsa;
        this.crudMaterial = crudMaterial;
        this.mapper = mapper;
    }

    @Override
    public List<RecetaBolsaDto> getAll() {
        return mapper.toDtoList(recetaBolsa.findAll());
    }

    @Override
    public Optional<RecetaBolsaDto> getById(Integer idBolsa, Integer idMaterial) {
        RecetaBolsaPK pk = new RecetaBolsaPK(idBolsa, idMaterial);

        System.out.println("PK BUSCADA = " + pk.getIdBolsa() + " - " + pk.getIdMaterial());
        return recetaBolsa.findById(pk)
                .map(mapper::toDto);
    }

    @Override
    public RecetaBolsaDto save(RecetaBolsaDto dto) {
        RecetaBolsaEntity entity = new RecetaBolsaEntity();
        entity.setId(new RecetaBolsaPK(
                dto.bolsa().id(),
                dto.material().id()
        ));
        entity.setBolsa(crudBolsa.findById(dto.bolsa().id())
                .orElseThrow(() -> new RuntimeException("Bolsa no encontrada")));

        entity.setMaterial(crudMaterial.findById(dto.material().id())
                .orElseThrow(() -> new RuntimeException("Material no encontrada")));

        entity.setCantidadRequerida(dto.cantidadRequerida());

        return mapper.toDto(recetaBolsa.save(entity));
    }

    @Override
    public Optional<RecetaBolsaDto> update(Integer idBolsa, Integer idMaterial, RecetaBolsaDto dto) {
        RecetaBolsaPK pk = new RecetaBolsaPK(idBolsa, idMaterial);

        System.out.println("BUSCANDO PK = " + idBolsa + " - " + idMaterial);

        return recetaBolsa.findById(pk)
                .map(entity -> {
                    System.out.println("REGISTRO ENCONTRADO");
                    entity.setCantidadRequerida(dto.cantidadRequerida());
                    return mapper.toDto(recetaBolsa.save(entity));
                });
    }

    @Override
    public boolean delete(Integer idBolsa, Integer idMaterial) {
        RecetaBolsaPK pk = new RecetaBolsaPK(idBolsa, idMaterial);
        if (recetaBolsa.existsById(pk)) {
            recetaBolsa.deleteById(pk);
            return true;
        }
        return false;
    }
}
