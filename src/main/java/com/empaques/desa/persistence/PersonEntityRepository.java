package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.PersonDto;
import com.empaques.desa.domain.repository.PersonRepository;
import com.empaques.desa.persistence.crud.CrudPersonEntity;
import com.empaques.desa.persistence.mapper.PersonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonEntityRepository implements PersonRepository {
    private final CrudPersonEntity crudPersonEntity;
    private final PersonMapper personMapper;

    public PersonEntityRepository(CrudPersonEntity crudPersonEntity, PersonMapper personMapper) {
        this.crudPersonEntity = crudPersonEntity;
        this.personMapper = personMapper;
    }

    @Override
    public List<PersonDto> getAll() {
        return this.personMapper.toDto(this.crudPersonEntity.findAll());
    }

    @Override
    public Optional<PersonDto> getById(Integer id) {
        return crudPersonEntity.findById(id)
                .map(personMapper::toDto);
    }

    @Override
    public PersonDto seva(PersonDto dto) {
        return Optional.of(dto)
                .map(personMapper::toEntity)
                .map(crudPersonEntity::save)
                .map(personMapper::toDto)
                .orElseThrow();
    }

    @Override
    public Optional<PersonDto> update(Integer id, PersonDto dto) {
        return crudPersonEntity.findById(id)
                .map(personEntity -> {
                    personEntity.setName(dto.name());
                    personEntity.setPhone(dto.phone());
                    personEntity.setMail(dto.mail());
                    personEntity.setAaddress(dto.aaddress());
                    return personEntity;
                })
                .map(crudPersonEntity::save)
                .map(personMapper::toDto);
    }

    @Override
    public boolean delete(Integer id) {
        return Optional.of(id)
                .filter(crudPersonEntity::existsById)
                .map(validId ->{
                    crudPersonEntity.deleteById(validId);
                    return true;
                })
                .orElse(false);
    }
}
