package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.EstadoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CrudEstadoEntity extends CrudRepository<EstadoEntity, Integer> {
    Optional<EstadoEntity> findByName(String name);
}
