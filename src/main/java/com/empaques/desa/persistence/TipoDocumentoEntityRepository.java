package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.TipoDocumentoDto;
import com.empaques.desa.domain.repository.TipoDocumentoRepository;
import com.empaques.desa.persistence.crud.CrudTipoDocumentoEntity;
import com.empaques.desa.persistence.mapper.TipoDocumentoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TipoDocumentoEntityRepository implements TipoDocumentoRepository {
    private final CrudTipoDocumentoEntity crudDocumentType;
    private final TipoDocumentoMapper documentTypeMapper;

    public TipoDocumentoEntityRepository(CrudTipoDocumentoEntity crudDocumentType, TipoDocumentoMapper documentTypeMapper) {
        this.crudDocumentType = crudDocumentType;
        this.documentTypeMapper = documentTypeMapper;
    }

    @Override
    public List<TipoDocumentoDto> getAll() {
        return documentTypeMapper.toDtoList(crudDocumentType.findAll());
    }

    @Override
    public Optional<TipoDocumentoDto> getById(Integer id) {
        return crudDocumentType.findById(id)
                .map(documentTypeMapper::toDto);
    }

    @Override
    public TipoDocumentoDto save(TipoDocumentoDto dto) {
        return Optional.of(dto)
                .map(documentTypeMapper::toEntity)
                .map(crudDocumentType::save)
                .map(documentTypeMapper::toDto)
                .orElseThrow();
    }

    @Override
    public Optional<TipoDocumentoDto> update(Integer id, TipoDocumentoDto dto) {
        return crudDocumentType.findById(id)
                .map(entity -> {
                    entity.setName(dto.name());
                    return entity;
                })
                .map(crudDocumentType::save)
                .map(documentTypeMapper::toDto);
    }

    @Override
    public boolean delete(Integer id) {
        return Optional.of(id)
                .filter(crudDocumentType::existsById)
                .map(validId -> {
                    crudDocumentType.deleteById(validId);
                    return true;
                })
                .orElse(false);
    }
}
