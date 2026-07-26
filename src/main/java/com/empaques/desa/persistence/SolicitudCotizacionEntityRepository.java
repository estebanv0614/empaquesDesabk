package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.SolicitudCotizacionDto;
import com.empaques.desa.domain.dto.SolicitudCotizacionRequestDto;
import com.empaques.desa.domain.repository.SolicitudCotizacionRepository;
import com.empaques.desa.persistence.crud.CrudEstadoEntity;
import com.empaques.desa.persistence.crud.CrudSolicitudCotizacionEntity;
import com.empaques.desa.persistence.entity.DetalleSolicitudEntity;
import com.empaques.desa.persistence.entity.SolicitudCotizacionEntity;
import com.empaques.desa.persistence.mapper.SolicitudCotizacionMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class SolicitudCotizacionEntityRepository implements SolicitudCotizacionRepository {
    private static final Integer ESTADO_PENDENTE_ID = 2;

    private final CrudSolicitudCotizacionEntity solicitud;
    private final CrudEstadoEntity  estado;
    private final SolicitudCotizacionMapper  solicitudMapper;

    public SolicitudCotizacionEntityRepository(CrudSolicitudCotizacionEntity solicitud, CrudEstadoEntity estado, SolicitudCotizacionMapper solicitudMapper) {
        this.solicitud = solicitud;
        this.estado = estado;
        this.solicitudMapper = solicitudMapper;
    }

    @Override
    public List<SolicitudCotizacionDto> getAll() {
        return solicitudMapper.toDtoList(solicitud.findAll());
    }

    @Override
    public Optional<SolicitudCotizacionDto> getById(Integer id) {
        return solicitud.findById(id)
                .map(solicitudMapper::toDto);
    }

    @Override
    public SolicitudCotizacionDto save(SolicitudCotizacionRequestDto dto) {
        SolicitudCotizacionEntity entity = new SolicitudCotizacionEntity();
        entity.setName(dto.name());
        entity.setPhone(dto.phone());
        entity.setMail(dto.mail());
        entity.setCity(dto.city());
        entity.setAddress(dto.address());
        entity.setObservacion(dto.observacion());

        entity.setEstado(
                estado.findById(ESTADO_PENDENTE_ID)
                        .orElseThrow(() -> new RuntimeException("Estado PENDIENTE no configurado"))
        );

        List<DetalleSolicitudEntity> detalles = dto.detalles().stream().map(d -> {
            DetalleSolicitudEntity detalle = new DetalleSolicitudEntity();
            detalle.setSolicitud(entity);
            detalle.setDescripcionProducto(d.descripcionProducto());
            detalle.setCantidadEstimada(d.cantidadEstimada());
            return detalle;
        }).toList();

        entity.setDetalles(detalles);

        return solicitudMapper.toDto(solicitud.save(entity));
    }

    @Override
    public Optional<SolicitudCotizacionDto> update(Integer id, Integer idEstado) {
        return solicitud.findById(id)
                .map(entity -> {
                    entity.setEstado(
                            estado.findById(idEstado)
                                    .orElseThrow(() -> new RuntimeException("Estado no está disponible" + idEstado))
                    );
                    SolicitudCotizacionEntity updated = solicitud.save(entity);
                    return solicitudMapper.toDto(updated);
                });
    }

    @Override
    public boolean delete(Integer id) {
        return solicitud.findById(id)
                .map(entity -> {
                    entity.setDeletedAt(LocalDateTime.now());
                    solicitud.save(entity);
                    return true;
                }).orElse(false);
    }
}
