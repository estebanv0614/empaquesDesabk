package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CrudClientEntity extends CrudRepository<ClientEntity, Integer> {
    Optional<ClientEntity> findByPerson_IdPerson(Integer idPerson);
}
