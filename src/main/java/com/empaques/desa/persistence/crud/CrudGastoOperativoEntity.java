package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.GastoOperativoEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface CrudGastoOperativoEntity extends CrudRepository <GastoOperativoEntity, Integer> {
    List<GastoOperativoEntity> findByFechaGastoBetween(LocalDate desde, LocalDate hasta);
}
