package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.PersonDto;
import com.empaques.desa.domain.exception.PersonAlreadyExistsException;
import com.empaques.desa.domain.repository.PersonRepository;
import com.empaques.desa.persistence.crud.CrudPersonEntity;
import com.empaques.desa.persistence.crud.CrudTipoDocumentoEntity;
import com.empaques.desa.persistence.entity.PersonEntity;
import com.empaques.desa.persistence.mapper.PersonMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonEntityRepository implements PersonRepository {
    private final CrudPersonEntity crudPerson;
    private final PersonMapper personMapper;
    private final CrudTipoDocumentoEntity crudTipoDocumento;

    public PersonEntityRepository(CrudPersonEntity crudPerson, PersonMapper personMapper, CrudTipoDocumentoEntity crudTipoDocumento) {
        this.crudPerson = crudPerson;
        this.personMapper = personMapper;
        this.crudTipoDocumento = crudTipoDocumento;
    }

    @Override
    public List<PersonDto> getAll() {
        return personMapper.toDtoList(crudPerson.findAll());
    }

    @Override
    public Optional<PersonDto> getById(Integer id) {
        return crudPerson.findById(id)
                .map(personMapper::toDto);
    }

    @Override
    public PersonDto save(PersonDto dto) {
        if (this.crudPerson.findFirstBydocumentNumber(dto.documentNumber()) != null){
            throw new PersonAlreadyExistsException(dto.documentNumber());
        }

        PersonEntity entity = personMapper.toEntity(dto);
        entity.setTipoDocumento(
                crudTipoDocumento.findById(dto.tipoDocumento().id())
                        .orElseThrow(() ->new RuntimeException("Tipo de Documento no encontrado"))
        );
        return personMapper.toDto(crudPerson.save(entity));
    }

    @Override
    public Optional<PersonDto> update(Integer id, PersonDto dto) {
        return crudPerson.findById(id)
                .map(entity -> {
                    entity.setDocumentNumber((dto.documentNumber()));
                    entity.setName(dto.name());
                    entity.setPhone(dto.phone());
                    entity.setEmail(dto.email());
                    entity.setAddress(dto.address());
                    return entity;
                })
                .map(crudPerson::save)
                .map(personMapper::toDto);
    }

    @Override
    public boolean delete(Integer id) {
        return crudPerson.findById(id)
                .map(person -> {
                    person.setDeletedAt(LocalDateTime.now());
                    crudPerson.save(person);
                    return true;
                }).orElse(false);
    }

    @Override
    public boolean restore(Integer id) {
        return crudPerson.findByIdIncludingDeleted(id)
                .map(person -> {
                    person.setDeletedAt(null);
                    crudPerson.save(person);
                    return true;
                }).orElse(false);
    }
}
