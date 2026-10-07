package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.MaterialEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CrudMaterialEntity extends CrudRepository<MaterialEntity, Integer> {
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM MaterialEntity m WHERE m.idMaterial = :id")
    Optional<MaterialEntity> findByIdForUpdate(@Param("id") Integer id);
}
