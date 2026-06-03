package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.EstadoDto;
import com.empaques.desa.domain.service.EstadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estado")
public class EstadoController {
    private final EstadoService estadoService;

    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @GetMapping
    public List<EstadoDto>getAll() {
        return estadoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoDto> getById(@PathVariable Integer id) {
        return estadoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EstadoDto> create(@RequestBody EstadoDto dto) {
        return ResponseEntity.ok(estadoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoDto> update(@PathVariable Integer id, @RequestBody EstadoDto dto) {
        return estadoService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>delete(@PathVariable Integer id) {
        estadoService.delete(id);
        return ResponseEntity.ok().build();
    }

}
