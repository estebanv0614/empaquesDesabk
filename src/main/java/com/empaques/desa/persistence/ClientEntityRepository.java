package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.ClientDto;
import com.empaques.desa.domain.repository.ClientRepository;
import com.empaques.desa.persistence.crud.CrudClientEntity;
import com.empaques.desa.persistence.crud.CrudPersonEntity;
import com.empaques.desa.persistence.crud.CrudStateEntity;
import com.empaques.desa.persistence.entity.ClientEntity;
import com.empaques.desa.persistence.mapper.ClientMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientEntityRepository implements ClientRepository {

    private final CrudClientEntity crudClientEntity;
    private final CrudPersonEntity crudPersonEntity;
    private final CrudStateEntity crudStateEntity;
    private final ClientMapper clientMapper;

    public ClientEntityRepository(CrudClientEntity crudClientEntity, CrudPersonEntity crudPersonEntity, CrudStateEntity crudStateEntity, ClientMapper clientMapper) {
        this.crudClientEntity = crudClientEntity;
        this.crudPersonEntity = crudPersonEntity;
        this.crudStateEntity = crudStateEntity;
        this.clientMapper = clientMapper;
    }


    @Override
    public List<ClientDto> getAll() {
        return clientMapper.toDtoList(crudClientEntity.findAll());
    }

    @Override
    public Optional<ClientDto> getById(Integer id) {
        return crudClientEntity.findById(id)
                .map(clientMapper::toDto);
    }

    @Override
    public ClientDto save(ClientDto dto) {
        ClientEntity entity = clientMapper.toEntity(dto);
        entity.setPerson(
                crudPersonEntity.findById(dto.person().id())
                        .orElseThrow(() -> new RuntimeException("Persona no existe"))
        );
        entity.setState(
                crudStateEntity.findById(dto.state().id())
                        .orElseThrow(() -> new RuntimeException("Estado no existe"))
        );
        return clientMapper.toDto(crudClientEntity.save(entity));
    }

    @Override
    public Optional<ClientDto> update(Integer id, ClientDto dto) {
        return crudClientEntity.findById(id)
                .map(entity -> {
                    entity.setCompany(dto.company());
                    if (dto.person() != null) {
                        entity.setPerson(
                                crudPersonEntity.findById(dto.person().id())
                                        .orElseThrow(() -> new RuntimeException("Persona no existe"))
                        );
                    }
                    if (dto.state() != null) {
                        entity.setState(
                                crudStateEntity.findById(dto.state().id())
                                        .orElseThrow(() -> new RuntimeException("Estado no existe"))
                        );
                    }
                    return entity;
                })
                .map(crudClientEntity::save)
                .map(clientMapper::toDto);
    }

    @Override
    public boolean delete(Integer id) {
        return Optional.of(id)
                .filter(crudClientEntity::existsById)
                .map(validId -> {
                    crudClientEntity.deleteById(validId);
                    return true;
                })
                .orElse(false);
    }

}
