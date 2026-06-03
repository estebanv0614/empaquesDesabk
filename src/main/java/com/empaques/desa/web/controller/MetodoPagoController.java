package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.MetodoPagoDto;
import com.empaques.desa.domain.service.MetodoPagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metodo-pago")
public class MetodoPagoController {
    private final MetodoPagoService metodoPagoService;

    public MetodoPagoController(MetodoPagoService metodoPagoService) {
        this.metodoPagoService = metodoPagoService;
    }

    @GetMapping
    public List<MetodoPagoDto> getAll() {
        return metodoPagoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoPagoDto> getById(@PathVariable Integer id) {
        return metodoPagoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MetodoPagoDto> create(@RequestBody MetodoPagoDto dto) {
        return ResponseEntity.ok(metodoPagoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPagoDto> update(@PathVariable Integer id, @RequestBody MetodoPagoDto dto) {
        return metodoPagoService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>delete(@PathVariable Integer id) {
        this.metodoPagoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
