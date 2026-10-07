package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.TipoGastoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CrudTipoGastoEntity extends CrudRepository<TipoGastoEntity, Integer> {
    Optional<TipoGastoEntity> findByNameIgnoreCase(String name);
}
