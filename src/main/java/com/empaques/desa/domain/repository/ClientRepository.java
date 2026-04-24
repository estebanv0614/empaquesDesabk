package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.ClientDto;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    List<ClientDto> getAll();
    Optional<ClientDto> getById(Integer id);
    ClientDto save(ClientDto dto);
    Optional<ClientDto> update(Integer id, ClientDto dto);
    boolean delete(Integer id);
}
