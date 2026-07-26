package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.SolicitudCotizacionDto;
import com.empaques.desa.domain.dto.SolicitudCotizacionRequestDto;

import java.util.List;
import java.util.Optional;

public interface SolicitudCotizacionRepository {
    List<SolicitudCotizacionDto> getAll();
    Optional<SolicitudCotizacionDto> getById(Integer id);
    SolicitudCotizacionDto save(SolicitudCotizacionRequestDto dto);
    Optional<SolicitudCotizacionDto> update(Integer id, Integer idEstado);
    boolean delete(Integer id);
}
