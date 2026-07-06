package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.DocumentoComercialDto;
import com.empaques.desa.domain.repository.DocumentoComercialRepository;
import com.empaques.desa.persistence.crud.*;
import com.empaques.desa.persistence.entity.DocumentoComercialEntity;
import com.empaques.desa.persistence.mapper.DocumentoComercialMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public class DocumentoComercialEntityRepository implements DocumentoComercialRepository {
    private final CrudDocumentoComercialEntity crudDocumentoComercial;
    private final CrudTipoDocumentoEntity crudTipoDocumento;
    private final CrudClientEntity crudClient;
    private final CrudUserEntity  crudUser;
    private final CrudMetodoPagoEntity crudMetodoPago;
    private final CrudEstadoEntity crudEstado;
    private final DocumentoComercialMapper comercialMapper;

    public DocumentoComercialEntityRepository(CrudDocumentoComercialEntity crudDocumentoComercial, CrudTipoDocumentoEntity crudTipoDocumento, CrudClientEntity crudClient, CrudUserEntity crudUser, CrudMetodoPagoEntity crudMetodoPago, CrudEstadoEntity crudEstado, DocumentoComercialMapper comercialMapper) {
        this.crudDocumentoComercial = crudDocumentoComercial;
        this.crudTipoDocumento = crudTipoDocumento;
        this.crudClient = crudClient;
        this.crudUser = crudUser;
        this.crudMetodoPago = crudMetodoPago;
        this.crudEstado = crudEstado;
        this.comercialMapper = comercialMapper;
    }

    @Override
    public List<DocumentoComercialDto> getAll() {
        return comercialMapper.toDtoList(crudDocumentoComercial.findAll());
    }

    @Override
    public Optional<DocumentoComercialDto> getById(Integer id) {
        return crudDocumentoComercial.findById(id)
                .map(comercialMapper::toDto);
    }

    @Override
    public DocumentoComercialDto save(DocumentoComercialDto dto) {
        DocumentoComercialEntity entity = comercialMapper.toEntity(dto);

        entity.setTipoDocumento(
                crudTipoDocumento.findById(dto.tipoDocumento().id())
                        .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"))
        );

        entity.setClient(
                crudClient.findById(dto.client().id())
                        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"))
        );

        entity.setUser(crudUser.findById(dto.user().id())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
        );

        entity.setMetodoPago(crudMetodoPago.findById(dto.metodoPago().id())
                .orElseThrow(() -> new RuntimeException("Metodo de pago no encontrado"))
        );

        entity.setEstado(
                crudEstado.findById(dto.estado().id())
                        .orElseThrow(() -> new RuntimeException("Estado de pago no encontrado"))
        );
        return comercialMapper.toDto(crudDocumentoComercial.save(entity));
    }

    @Override
    public Optional<DocumentoComercialDto> update(Integer id, DocumentoComercialDto dto) {
        return crudDocumentoComercial.findById(id)
                .map( entity -> {
                    entity.setNumeroFactura(dto.numeroFactura());
                    entity.setSubtotal(dto.subtotal());
                    entity.setIva(dto.iva());
                    entity.setTotal(dto.total());
                    DocumentoComercialEntity updated = crudDocumentoComercial.save(entity);
                    return comercialMapper.toDto(updated);
                });
    }

    @Override
    public boolean delete(Integer id) {
        return crudDocumentoComercial.findById(id)
                .map(entity -> {
                    entity.setDeletedAt(LocalDateTime.now());
                    crudDocumentoComercial.save(entity);
                    return true;
                }).orElse(false);
    }
}
