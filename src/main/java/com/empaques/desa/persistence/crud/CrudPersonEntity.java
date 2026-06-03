package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.PersonEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CrudPersonEntity extends CrudRepository<PersonEntity, Integer> {
    PersonEntity findFirstBydocumentNumber(String documentNumber);

    @Query("SELECT p FROM PersonEntity p WHERE p.idPerson = :id")
    Optional<PersonEntity> findByIdIncludingDeleted(Integer id);
}
