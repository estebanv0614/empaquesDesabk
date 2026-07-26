package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.SolicitudCotizacionDto;
import com.empaques.desa.domain.dto.SolicitudCotizacionRequestDto;
import com.empaques.desa.domain.repository.SolicitudCotizacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitudCotizacionService {
    private final SolicitudCotizacionRepository solicitudCotizacion;
    private final EmailNotificacionService emailNotificacion;

    public SolicitudCotizacionService(SolicitudCotizacionRepository solicitudCotizacion, EmailNotificacionService emailNotificacion) {
        this.solicitudCotizacion = solicitudCotizacion;
        this.emailNotificacion = emailNotificacion;
    }

    public List<SolicitudCotizacionDto> getAll() {
        return solicitudCotizacion.getAll();
    }

    public Optional<SolicitudCotizacionDto> getById(Integer id) {
        return solicitudCotizacion.getById(id);
    }

    public SolicitudCotizacionDto save(SolicitudCotizacionRequestDto dto) {
        SolicitudCotizacionDto guardado = solicitudCotizacion.save(dto);
        emailNotificacion.notificarNuevaSolicitud(guardado);
        return guardado;
    }

    public Optional<SolicitudCotizacionDto> update(Integer id, Integer idEstado) {
        return solicitudCotizacion.update(id, idEstado);
    }

    public boolean delete(Integer id) {
        return solicitudCotizacion.delete(id);
    }
}
