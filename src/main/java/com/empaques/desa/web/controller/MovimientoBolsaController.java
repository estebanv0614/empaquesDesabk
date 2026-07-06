package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.MovimientoBolsaDto;
import com.empaques.desa.domain.service.MovimientoBolsaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos-bolsa")
public class MovimientoBolsaController {
    private final MovimientoBolsaService movimientoBolsaService;

    public MovimientoBolsaController(MovimientoBolsaService movimientoBolsaService) {
        this.movimientoBolsaService = movimientoBolsaService;
    }

    @GetMapping
    public List<MovimientoBolsaDto> getAll() {
        return movimientoBolsaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoBolsaDto> getById(@PathVariable Integer id) {
        return movimientoBolsaService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MovimientoBolsaDto> create(@RequestBody MovimientoBolsaDto dto) {
        return ResponseEntity.ok(movimientoBolsaService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoBolsaDto> update(@PathVariable Integer id, @RequestBody MovimientoBolsaDto dto) {
        return movimientoBolsaService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MovimientoBolsaDto> delete(@PathVariable Integer id) {
        boolean deleted = movimientoBolsaService.delete(id);

        if (deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
