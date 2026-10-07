package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CrudEmployeeEntity extends CrudRepository<EmployeeEntity, Integer> {
    List<EmployeeEntity> findByEstado_NameIgnoreCase(String name);
}
