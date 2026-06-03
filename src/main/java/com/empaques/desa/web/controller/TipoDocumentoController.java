package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.TipoDocumentoDto;
import com.empaques.desa.domain.service.TipoDocumentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/document-types")
public class TipoDocumentoController {
    private final TipoDocumentoService documentTypeService;

    public TipoDocumentoController(TipoDocumentoService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    @GetMapping
    public List<TipoDocumentoDto> getAll() {
        return documentTypeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoDocumentoDto> getById(@PathVariable Integer id) {
        return documentTypeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoDocumentoDto> create(@RequestBody TipoDocumentoDto dto) {
        return ResponseEntity.ok(documentTypeService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoDocumentoDto> update(@PathVariable Integer id, @RequestBody TipoDocumentoDto dto) {
        return documentTypeService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>delete(@PathVariable Integer id) {
        this.documentTypeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
