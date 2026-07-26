package com.empaques.desa.domain.service;


import com.empaques.desa.domain.dto.LoginDto;
import com.empaques.desa.domain.dto.RolDto;
import com.empaques.desa.domain.dto.UserDto;
import com.empaques.desa.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAll() {
        return userRepository.getAll();
    }

    public Optional<UserDto> getById(Integer id) {
        return userRepository.getById(id);
    }

    public UserDto save(UserDto dto) {
        return userRepository.save(dto);
    }

    public Optional<UserDto> update(Integer id, UserDto dto) {
        return userRepository.update(id, dto);
    }

    public boolean delete(Integer id) {
        return userRepository.delete(id);
    }

    public boolean restore(Integer id){
        return userRepository.restore(id);
    }

    public UserDto login(LoginDto dto) {
        return userRepository.login(dto);
    }

    public boolean deactivate(Integer id){
        return userRepository.deactivate(id);
    }

    public boolean activate(Integer id){
        return userRepository.activate(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = this.userRepository.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Credenciales inválidas"));

        return User.builder()
                .username(userDto.username())
                .password(userDto.password())
                .disabled(!userDto.activo())
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .roles(userDto.roles().stream()
                        .map(RolDto::rol)
                        .toArray(String[]::new))
                .build();
    }
    public Optional<UserDto> getByUsername(String username) {
        return userRepository.getByUsername(username);
    }
}
