package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;

public interface CrudClientEntity extends CrudRepository<ClientEntity, Integer> {
}
