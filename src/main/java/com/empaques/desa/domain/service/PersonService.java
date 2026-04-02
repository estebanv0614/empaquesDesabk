package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.PersonDto;
import com.empaques.desa.domain.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonDto> getAll(){
        return this.personRepository.getAll();
    }

    public Optional<PersonDto> getById(Integer id){
        return this.personRepository.getById(id);
    }

    public PersonDto seva(PersonDto dto){
        return personRepository.seva(dto);
    }

    public Optional<PersonDto> update(Integer id, PersonDto dto){
        return this.personRepository.update(id, dto);
    }

    public boolean delete(Integer id){
        return personRepository.delete(id);
    }
}
