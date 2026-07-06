package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.RecetaBolsaDto;
import com.empaques.desa.domain.service.RecetaBolsaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recetas-bolsa")
public class RecetaBolsaController {
    private final RecetaBolsaService recetaBolsaService;

    public RecetaBolsaController(RecetaBolsaService recetaBolsaService) {
        this.recetaBolsaService = recetaBolsaService;
    }

    @GetMapping
    public List<RecetaBolsaDto> getAll() {
        return recetaBolsaService.getAll();
    }

    @GetMapping("/{idBolsa}/{idMaterial}")
    public ResponseEntity<RecetaBolsaDto> getById(
            @PathVariable("idBolsa") Integer idBolsa,
            @PathVariable("idMaterial") Integer idMaterial) {
        return recetaBolsaService.getById(idBolsa, idMaterial)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RecetaBolsaDto> create(@RequestBody RecetaBolsaDto dto) {
        return ResponseEntity.ok(recetaBolsaService.save(dto));
    }

    @PutMapping("/{idBolsa}/{idMaterial}")
    public ResponseEntity<RecetaBolsaDto> update(
            @PathVariable("idBolsa") Integer idBolsa,
            @PathVariable("idMaterial") Integer idMaterial,
            @RequestBody RecetaBolsaDto dto) {
        System.out.println("ENTRO AL UPDATE: " + idBolsa + " - " + idMaterial);
        return recetaBolsaService.update(idBolsa, idMaterial, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idBolsa}/{idMaterial}")
    public ResponseEntity<Void> delete(
            @PathVariable("idBolsa") Integer idBolsa,
            @PathVariable("idMaterial") Integer idMaterial) {
        if (!recetaBolsaService.delete(idBolsa, idMaterial)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
