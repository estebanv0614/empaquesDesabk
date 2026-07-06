package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.DetalleDocumentoDto;
import com.empaques.desa.domain.repository.DetalleDocumentoRepository;
import com.empaques.desa.persistence.crud.CrudBolsaEntity;
import com.empaques.desa.persistence.crud.CrudDetalleDocumentoEntity;
import com.empaques.desa.persistence.crud.CrudDocumentoComercialEntity;
import com.empaques.desa.persistence.entity.DetalleDocumentoEntity;
import com.empaques.desa.persistence.mapper.DetalleDocumentoMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class DetalleDocumentoEntityRepository implements DetalleDocumentoRepository {

    private final CrudDetalleDocumentoEntity crudDetalleDocumento;
    private final CrudDocumentoComercialEntity crudDocumentoComercial;
    private final CrudBolsaEntity crudBolsa;
    private final DetalleDocumentoMapper detalleDocumentoMapper;

    public DetalleDocumentoEntityRepository(CrudDetalleDocumentoEntity crudDetalleDocumento, CrudDocumentoComercialEntity crudDocumentoComercial, CrudBolsaEntity crudBolsa, DetalleDocumentoMapper detalleDocumentoMapper) {
        this.crudDetalleDocumento = crudDetalleDocumento;
        this.crudDocumentoComercial = crudDocumentoComercial;
        this.crudBolsa = crudBolsa;
        this.detalleDocumentoMapper = detalleDocumentoMapper;
    }

    @Override
    public List<DetalleDocumentoDto> getAll() {
        return detalleDocumentoMapper.toDtoList(crudDetalleDocumento.findAll());
    }

    @Override
    public Optional<DetalleDocumentoDto> getById(Integer id) {
        return crudDetalleDocumento.findById(id)
                .map(detalleDocumentoMapper::toDto);
    }

    @Override
    public DetalleDocumentoDto save(DetalleDocumentoDto dto) {
        DetalleDocumentoEntity entity = detalleDocumentoMapper.toEntity(dto);

        entity.setDocumentoComercial(
                crudDocumentoComercial.findById(dto.documentoComercial().id())
                        .orElseThrow(() -> new RuntimeException("Documento no encontrado"))
        );

        entity.setBolsa(
                crudBolsa.findById(dto.bolsa().id())
                        .orElseThrow(() -> new RuntimeException("Bolsa no encontrado"))
        );
        return detalleDocumentoMapper.toDto(crudDetalleDocumento.save(entity));
    }

    @Override
    public Optional<DetalleDocumentoDto> update(Integer id, DetalleDocumentoDto dto) {
        return crudDetalleDocumento.findById(id)
                .map(entity -> {
                    entity.setCantidad(dto.cantidad());
                    entity.setPrecioUnitarioSnapshot(dto.precioUnitarioSnapshot());
                    entity.setSubtotal(dto.subtotal());
                    DetalleDocumentoEntity updated = crudDetalleDocumento.save(entity);
                    return detalleDocumentoMapper.toDto(updated);
                });
    }

    @Override
    public boolean delete(Integer id) {
        return crudDetalleDocumento.findById(id)
                .map(entity -> {
                    entity.setDeletedAt(LocalDateTime.now());
                    crudDetalleDocumento.save(entity);
                    return true;
                }).orElse(false);
    }
}
