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


    public List<PersonDto> getAll() {
        return personRepository.getAll();
    }

    public Optional<PersonDto> getById(Integer id) {
        return personRepository.getById(id);
    }

    public PersonDto save(PersonDto dto) {
        return personRepository.save(dto);
    }

    public Optional<PersonDto> update(Integer id, PersonDto dto) {
        return personRepository.update(id, dto);
    }

    public boolean delete(Integer id) {
        return personRepository.delete(id);
    }

    public boolean restore(Integer id) {
        return personRepository.restore(id);
    }

    public Optional<PersonDto> search(String email, String phone) {
        return personRepository.search(email, phone);
    }
}
