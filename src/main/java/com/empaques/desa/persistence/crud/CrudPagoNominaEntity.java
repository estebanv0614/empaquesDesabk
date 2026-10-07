package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.EmployeeEntity;
import com.empaques.desa.persistence.entity.PagoNominaEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface CrudPagoNominaEntity extends CrudRepository<PagoNominaEntity, Integer> {
    List<PagoNominaEntity> findByFechaInicio(LocalDate fechaInicio);
    boolean existsByEmployeeAndFechaInicio(EmployeeEntity employee, LocalDate fechaInicio);
}
