package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.BolsaDto;
import com.empaques.desa.domain.service.BolsaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bolsa")
public class BolsaController {
    private final BolsaService bolsaService;

    public BolsaController(BolsaService bolsaService) {
        this.bolsaService = bolsaService;
    }

    @GetMapping
    public List<BolsaDto> getAll() {
        return this.bolsaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BolsaDto> getById(@PathVariable Integer id) {
        return bolsaService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<BolsaDto> create (@RequestBody BolsaDto dto){
        return ResponseEntity.ok(bolsaService.save(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<BolsaDto> update(@PathVariable Integer id, @RequestBody BolsaDto dto) {
        return bolsaService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>delete(@PathVariable Integer id) {
        bolsaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
