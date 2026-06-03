package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.EmployeeDto;
import com.empaques.desa.domain.repository.EmployeeRepository;
import com.empaques.desa.persistence.crud.CrudEmployeeEntity;
import com.empaques.desa.persistence.crud.CrudEstadoEntity;
import com.empaques.desa.persistence.crud.CrudPersonEntity;
import com.empaques.desa.persistence.entity.EmployeeEntity;
import com.empaques.desa.persistence.mapper.EmployeeMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeEntityRepository implements EmployeeRepository {
    private final CrudEmployeeEntity crudEmployee;
    private final CrudPersonEntity personEntity;
    private final CrudEstadoEntity estadoEntity;
    private final EmployeeMapper employeeMapper;

    public EmployeeEntityRepository(CrudEmployeeEntity crudEmployee, CrudPersonEntity personEntity, CrudEstadoEntity estadoEntity, EmployeeMapper employeeMapper) {
        this.crudEmployee = crudEmployee;
        this.personEntity = personEntity;
        this.estadoEntity = estadoEntity;
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
        entity.setPerson(
                personEntity.findById(dto.person().id())
                        .orElseThrow(() -> new RuntimeException("Person with id " + dto.person().id() + " not foind"))
        );
        entity.setEstado(
                estadoEntity.findById(dto.estado().id())
                        .orElseThrow(() -> new RuntimeException("Estado no esta disponible " + dto.estado().id()))
        );
        return employeeMapper.toDto(crudEmployee.save(entity));
    }

    @Override
    public Optional<EmployeeDto> update(Integer id, EmployeeDto dto) {
        return crudEmployee.findById(id)
                .map(entity -> {
                    entity.setPosition(dto.position());
                    entity.setSalary(dto.salary());
                    entity.setFechaIngreso(dto.fechaIngreso());

                    entity.setEstado(
                            estadoEntity.findById(dto.estado().id())
                                    .orElseThrow(() -> new RuntimeException("Estado no esta disponible " + dto.estado().id()))
                    );
                    EmployeeEntity updated = crudEmployee.save(entity);
                    return employeeMapper.toDto(updated);
                });
    }

    @Override
    public boolean delete(Integer id) {
        return crudEmployee.findById(id)
                .map(entity -> {
                    entity.setDeletedAt(LocalDateTime.now());
                    crudEmployee.save(entity);
                    return true;
                }).orElse(false);
    }
}
