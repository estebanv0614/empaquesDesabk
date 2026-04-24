package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.MaterialEntity;
import org.springframework.data.repository.CrudRepository;

public interface CrudMaterialEntity extends CrudRepository<MaterialEntity, Integer> {
}
