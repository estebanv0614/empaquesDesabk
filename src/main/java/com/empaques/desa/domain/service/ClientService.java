package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.ClientDto;
import com.empaques.desa.domain.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientDto> getAll() {
        return clientRepository.getAll();
    }

    public Optional<ClientDto> getById(Integer id) {
        return clientRepository.getById(id);
    }

    public ClientDto save(ClientDto dto) {
        return clientRepository.save(dto);
    }

    public Optional<ClientDto> update(Integer id, ClientDto dto) {
        return clientRepository.update(id, dto);
    }

    public boolean delete(Integer id) {
        return clientRepository.getById(id)
                .map(c -> {
                    clientRepository.delete(id);
                    return true;
                }).orElse(false);
    }
}
