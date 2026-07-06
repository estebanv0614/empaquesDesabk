package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.OrdenProduccionDto;
import com.empaques.desa.domain.service.OrdenProduccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes-produccion")
public class OrdenProduccionController {
    private final OrdenProduccionService produccionService;

    public OrdenProduccionController(OrdenProduccionService produccionService) {
        this.produccionService = produccionService;
    }

    @GetMapping
    public List<OrdenProduccionDto> getAll() {
        return produccionService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenProduccionDto> getById(@PathVariable Integer id) {
        return produccionService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<OrdenProduccionDto> create(@RequestBody OrdenProduccionDto dto) {
        return ResponseEntity.ok(produccionService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenProduccionDto> update(@PathVariable Integer id, @RequestBody OrdenProduccionDto dto) {
        return produccionService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = produccionService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
