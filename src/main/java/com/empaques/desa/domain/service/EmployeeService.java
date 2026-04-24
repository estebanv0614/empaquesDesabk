package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.EmployeeDto;
import com.empaques.desa.domain.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDto> getAll() {
        return this.employeeRepository.getAll();
    }

    public Optional<EmployeeDto> getById(Integer id) {
        return this.employeeRepository.getById(id);
    }

    public EmployeeDto save(EmployeeDto dto){
        return this.employeeRepository.save(dto);
    }

    public Optional<EmployeeDto> update(Integer id, EmployeeDto dto) {
        return this.employeeRepository.update(id, dto);
    }

    public void delete(Integer id) {
        this.employeeRepository.delete(id);
    }
}
