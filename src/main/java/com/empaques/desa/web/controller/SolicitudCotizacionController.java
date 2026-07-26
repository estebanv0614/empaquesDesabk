package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.SolicitudCotizacionDto;
import com.empaques.desa.domain.dto.SolicitudCotizacionRequestDto;
import com.empaques.desa.domain.service.SolicitudCotizacionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/solicitudes-cotizacion")
public class SolicitudCotizacionController {
    private final SolicitudCotizacionService solicitudCotizacion;

    public SolicitudCotizacionController(SolicitudCotizacionService solicitudCotizacion) {
        this.solicitudCotizacion = solicitudCotizacion;
    }

    @PostMapping
    public ResponseEntity<SolicitudCotizacionDto> create(@Valid @RequestBody SolicitudCotizacionRequestDto dto) {
        return ResponseEntity.ok(solicitudCotizacion.save(dto));
    }

    @GetMapping
    public List<SolicitudCotizacionDto> getAll() {
        return solicitudCotizacion.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudCotizacionDto> getById(@PathVariable Integer id) {
        return solicitudCotizacion.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<SolicitudCotizacionDto> update(@PathVariable Integer id, @RequestBody Map<String, Integer> body) {
        Integer idEstado = body.get("idEstado");
        return solicitudCotizacion.update(id, idEstado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = solicitudCotizacion.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
