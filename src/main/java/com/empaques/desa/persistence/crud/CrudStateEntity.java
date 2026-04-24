package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.StateEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CrudStateEntity extends CrudRepository<StateEntity , Integer> {
    Optional<StateEntity> findByName(String name);
}
