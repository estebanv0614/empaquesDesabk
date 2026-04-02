package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.PersonDto;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    List<PersonDto> getAll();
    Optional<PersonDto> getById(Integer id);
    PersonDto seva(PersonDto dto);
    Optional<PersonDto> update(Integer id, PersonDto dto);
    boolean delete(Integer id);
}
