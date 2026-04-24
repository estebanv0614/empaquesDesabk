package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;

public interface CrudPersonEntity extends CrudRepository<PersonEntity, Integer> { }
