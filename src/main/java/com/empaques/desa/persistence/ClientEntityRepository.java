package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.ClientDto;
import com.empaques.desa.domain.dto.EmployeeDto;
import com.empaques.desa.domain.repository.ClientRepository;
import com.empaques.desa.persistence.crud.CrudClientEntity;
import com.empaques.desa.persistence.crud.CrudEstadoEntity;
import com.empaques.desa.persistence.crud.CrudPersonEntity;
import com.empaques.desa.persistence.entity.ClientEntity;
import com.empaques.desa.persistence.mapper.ClientMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientEntityRepository implements ClientRepository {
    private final CrudClientEntity crudClient;
    private final CrudPersonEntity crudPerson;
    private final CrudEstadoEntity crudEstado;
    private final ClientMapper clientMapper;

    public ClientEntityRepository(CrudClientEntity crudClient, CrudPersonEntity crudPerson, CrudEstadoEntity crudEstado, ClientMapper clientMapper) {
        this.crudClient = crudClient;
        this.crudPerson = crudPerson;
        this.crudEstado = crudEstado;
        this.clientMapper = clientMapper;
    }

    @Override
    public List<ClientDto> getAll() {
        return clientMapper.toDtoList(crudClient.findAll());
    }

    @Override
    public Optional<ClientDto> getById(Integer id) {
        return crudClient.findById(id)
                .map(clientMapper::toDto);
    }

    @Override
    public ClientDto save(ClientDto dto) {
        ClientEntity entity = clientMapper.toEntity(dto);
        entity.setPerson(
                crudPerson.findById(dto.person().id())
                        .orElseThrow(() -> new RuntimeException("Person not found " + dto.person().id()))
        );
        entity.setEstado(
                crudEstado.findById(dto.estado().id())
                        .orElseThrow(() -> new RuntimeException("Estado not found " + dto.estado().id()))
        );
        return clientMapper.toDto(crudClient.save(entity));
    }

    @Override
    public Optional<ClientDto> update(Integer id, ClientDto dto) {
        return crudClient.findById(id)
                .map( entity -> {
                    entity.setEmpresa(dto.empresa());

                    entity.setEstado(
                            crudEstado.findById(dto.estado().id())
                                    .orElseThrow(() -> new RuntimeException("Estado not found " + dto.estado().id()))
                    );
                    ClientEntity updated = crudClient.save(entity);
                    return clientMapper.toDto(updated);
                });
    }

    @Override
    public boolean delete(Integer id) {
        return crudClient.findById(id)
                .map(entity -> {
                    entity.setDeletedAt(LocalDateTime.now());
                    crudClient.save(entity);
                    return true;
                }).orElse(false);
    }

    @Override
    public Optional<ClientDto> getByPersonId(Integer idPerson) {
        return crudClient.findByPerson_IdPerson(idPerson)
                .map(clientMapper::toDto);
    }
}
