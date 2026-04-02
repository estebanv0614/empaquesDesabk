package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.UserDto;
import com.empaques.desa.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> findAll(){
        return userRepository.getAll();
    }

    public Optional<UserDto> getById(Integer id) {
        return this.userRepository.getById(id);
    }

    public UserDto save(UserDto dto){
        return userRepository.seva(dto);
    }

    public Optional<UserDto> update(Integer id, UserDto dto) {
        return userRepository.update(id, dto);
    }

    public boolean delete (Integer id) {
        return userRepository.getById(id)
                .map(u -> {
                    userRepository.delete(id);
                    return true;
                }).orElse(false);
    }
}
