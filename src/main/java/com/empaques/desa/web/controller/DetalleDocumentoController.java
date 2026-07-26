package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.DetalleDocumentoDto;
import com.empaques.desa.domain.service.DetalleDocumentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalle-documentos")
public class DetalleDocumentoController {
    private final DetalleDocumentoService detalleDocumento;

    public DetalleDocumentoController(DetalleDocumentoService detalleDocumento) {
        this.detalleDocumento = detalleDocumento;
    }

    @GetMapping
    public List<DetalleDocumentoDto> getAll() {
        return detalleDocumento.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleDocumentoDto> getById(@PathVariable Integer id) {
        return detalleDocumento.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DetalleDocumentoDto> create(@RequestBody DetalleDocumentoDto dto) {
        return ResponseEntity.ok(detalleDocumento.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleDocumentoDto> update(@PathVariable Integer id, @RequestBody DetalleDocumentoDto dto) {
        return detalleDocumento.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DetalleDocumentoDto> delete(@PathVariable Integer id) {
        boolean deleted = detalleDocumento.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
