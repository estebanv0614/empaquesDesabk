package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.MovimientoInventarioDto;
import com.empaques.desa.domain.service.MovimientoInventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos-inventario")
public class MovimientoInventarioController {
    private final MovimientoInventarioService movimientoInventarioService;

    public MovimientoInventarioController(MovimientoInventarioService movimientoInventarioService) {
        this.movimientoInventarioService = movimientoInventarioService;
    }

    @GetMapping
    public List<MovimientoInventarioDto> getAll() {
        return movimientoInventarioService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoInventarioDto> getById(@PathVariable Integer id) {
        return movimientoInventarioService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MovimientoInventarioDto> create(@RequestBody MovimientoInventarioDto dto) {
        return ResponseEntity.ok(movimientoInventarioService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoInventarioDto> update(@PathVariable Integer id, @RequestBody MovimientoInventarioDto dto) {
        return movimientoInventarioService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = movimientoInventarioService.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
