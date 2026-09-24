package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.BolsaEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CrudBolsaEntity extends CrudRepository<BolsaEntity, Integer> {
    List<BolsaEntity> findByEstado_NameIgnoreCase(String nameEstado);
}
