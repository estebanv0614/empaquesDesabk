package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.StateDto;
import com.empaques.desa.domain.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateService {
    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public List<StateDto> getAll(){
        return stateRepository.getAll();
    }

    public Optional<StateDto> getById(Integer id) {
        return stateRepository.getById(id);
    }

    public StateDto save(StateDto dto) {
        return stateRepository.save(dto);
    }

    public Optional<StateDto> update(Integer id, StateDto dto) {
        return stateRepository.update(id, dto);
    }

    public boolean delete(Integer id) {
        return stateRepository.delete(id);
    }
}
