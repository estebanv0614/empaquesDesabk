package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

public interface CrudEmployeeEntity extends CrudRepository<EmployeeEntity, Integer> {
}
