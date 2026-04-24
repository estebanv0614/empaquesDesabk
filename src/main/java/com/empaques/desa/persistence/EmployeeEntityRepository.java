package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.EmployeeDto;
import com.empaques.desa.domain.repository.EmployeeRepository;
import com.empaques.desa.persistence.crud.CrudEmployeeEntity;
import com.empaques.desa.persistence.crud.CrudPersonEntity;
import com.empaques.desa.persistence.crud.CrudStateEntity;
import com.empaques.desa.persistence.entity.EmployeeEntity;
import com.empaques.desa.persistence.entity.StateEntity;
import com.empaques.desa.persistence.mapper.EmployeeMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeEntityRepository implements EmployeeRepository {
    private final CrudEmployeeEntity crudEmployee;
    private final CrudPersonEntity personCrud;
    private final CrudStateEntity crudState;
    private final EmployeeMapper employeeMapper;

    public EmployeeEntityRepository(CrudEmployeeEntity crudEmployee, CrudPersonEntity personCrud, CrudStateEntity crudState, EmployeeMapper employeeMapper) {
        this.crudEmployee = crudEmployee;
        this.personCrud = personCrud;
        this.crudState = crudState;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<EmployeeDto> getAll() {
        return employeeMapper.toDtoList(crudEmployee.findAll());
    }

    @Override
    public Optional<EmployeeDto> getById(Integer id) {
        return crudEmployee.findById(id)
                .map(employeeMapper::toDto);
    }

    @Override
    public EmployeeDto save(EmployeeDto dto) {
        EmployeeEntity entity = employeeMapper.toEntity(dto);
        entity.setFechaIngreso(dto.fechaIngreso());
        entity.setPerson(
                personCrud.findById(dto.person().id())
                        .orElseThrow(() -> new RuntimeException("Persona no existe"))
        );
        entity.setState(
                crudState.findById(dto.state().id())
                        .orElseThrow(() -> new RuntimeException("Estado no existe"))
        );
        return employeeMapper.toDto(crudEmployee.save(entity));
    }

    @Override
    public Optional<EmployeeDto> update(Integer id, EmployeeDto dto) {
        return crudEmployee.findById(id)
                .map(entity -> {
                    if (dto.person() != null) {
                        entity.setPerson(
                                personCrud.findById(dto.person().id())
                                        .orElseThrow()
                        );
                    }
                    entity.setPosition(dto.position());
                    entity.setSalary(dto.salary());
                    entity.setFechaIngreso(dto.fechaIngreso());
                    if (dto.state() != null) {
                        entity.setState(
                                crudState.findById(dto.state().id())
                                        .orElseThrow()
                        );
                    }
                    return entity;
                })
                .map(crudEmployee::save)
                .map(employeeMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        EmployeeEntity entity = crudEmployee.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee no existe"));
        StateEntity inactive = crudState.findByName("INACTIVO")
                .orElseThrow(() -> new RuntimeException("Estado INACTIVO no existe"));
        entity.setState(inactive);
        entity.setDeletedAt(LocalDateTime.now());
        crudEmployee.save(entity);

    }
}
