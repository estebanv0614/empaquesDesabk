package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.EstadoEntity;
import org.springframework.data.repository.CrudRepository;

public interface CrudEstadoEntity extends CrudRepository<EstadoEntity, Integer> {
}
