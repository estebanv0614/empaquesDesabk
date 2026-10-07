package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.RecetaBolsaEntity;
import com.empaques.desa.persistence.entity.RecetaBolsaPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CrudRecetaBolsaEntity extends CrudRepository<RecetaBolsaEntity, RecetaBolsaPK> {
    @Query("SELECT r FROM RecetaBolsaEntity r WHERE r.bolsa.idBolsa = :idBolsa")
    List<RecetaBolsaEntity> findByIdBolsa(@Param("idBolsa") Integer idBolsa);
}
