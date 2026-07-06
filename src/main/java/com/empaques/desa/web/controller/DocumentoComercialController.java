package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.DocumentoComercialDto;
import com.empaques.desa.domain.service.DocumentoComercialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("documentos-comerciales")
public class DocumentoComercialController {
    private final DocumentoComercialService documentoComercial;

    public DocumentoComercialController(DocumentoComercialService documentoComercial) {
        this.documentoComercial = documentoComercial;
    }

    @GetMapping
    public List<DocumentoComercialDto>getAll() {
        return documentoComercial.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoComercialDto> getById(@PathVariable Integer id) {
        return documentoComercial.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DocumentoComercialDto> create(@RequestBody DocumentoComercialDto dto) {
        return ResponseEntity.ok(documentoComercial.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentoComercialDto> update(@PathVariable Integer id, @RequestBody DocumentoComercialDto dto) {
        return documentoComercial.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DocumentoComercialDto> delete(@PathVariable Integer id) {
        boolean deleted = documentoComercial.delete(id);
        if (deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
