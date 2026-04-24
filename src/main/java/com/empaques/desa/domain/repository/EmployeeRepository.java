package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.EmployeeDto;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    List<EmployeeDto> getAll();
    Optional<EmployeeDto> getById(Integer id);
    EmployeeDto save(EmployeeDto dto);
    Optional<EmployeeDto> update(Integer id, EmployeeDto dto);
    void delete(Integer id);
}
