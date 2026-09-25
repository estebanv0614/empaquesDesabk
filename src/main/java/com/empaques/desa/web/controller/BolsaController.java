package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.BolsaDto;
import com.empaques.desa.domain.dto.EstadoDto;
import com.empaques.desa.domain.service.BolsaService;
import com.empaques.desa.domain.service.ImagenService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/bolsas")
public class BolsaController {
    private final BolsaService bolsaService;
    private final ImagenService imagenService;

    public BolsaController(BolsaService bolsaService, ImagenService imagenService) {
        this.bolsaService = bolsaService;
        this.imagenService = imagenService;
    }

    @GetMapping
    public List<BolsaDto> getAll() {
        return bolsaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BolsaDto> getById(@PathVariable Integer id) {
        return bolsaService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/catalogo")
    public List<BolsaDto> getPublicoCatalogo() {
        return bolsaService.getPublicoCatalogo();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BolsaDto> create(
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam String tipo,
            @RequestParam(required = false) BigDecimal anchoCm,
            @RequestParam(required = false) BigDecimal largoCm,
            @RequestParam(required = false) BigDecimal calibre,
            @RequestParam BigDecimal precioBase,
            @RequestParam Integer idEstado,
            @RequestParam(required = false) MultipartFile imagen) throws IOException {

        String imagenUrl = imagenService.guardar(imagen);

        BolsaDto dto = new BolsaDto(
                null, name, description, tipo, anchoCm, largoCm, calibre,
                precioBase, imagenUrl, new EstadoDto(idEstado, null)
        );

        return ResponseEntity.ok(bolsaService.save(dto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BolsaDto> update(
            @PathVariable Integer id,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam String tipo,
            @RequestParam(required = false) BigDecimal anchoCm,
            @RequestParam(required = false) BigDecimal largoCm,
            @RequestParam(required = false) BigDecimal calibre,
            @RequestParam BigDecimal precioBase,
            @RequestParam Integer idEstado,
            @RequestParam(required = false) MultipartFile imagen) throws IOException {

        String imagenUrl = imagenService.guardar(imagen);

        BolsaDto dto = new BolsaDto(
                id, name, description, tipo, anchoCm, largoCm, calibre,
                precioBase, imagenUrl, new EstadoDto(idEstado, null)
        );

        return bolsaService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        System.out.println("ELIMINANDO BOLSA ID = " + id);
        boolean deleted = bolsaService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
