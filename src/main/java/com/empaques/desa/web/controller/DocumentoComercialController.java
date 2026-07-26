package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.DocumentoComercialDto;
import com.empaques.desa.domain.service.CotizacionPdfService;
import com.empaques.desa.domain.service.DocumentoComercialService;
import com.lowagie.text.DocumentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("documentos-comerciales")
public class DocumentoComercialController {
    private final DocumentoComercialService documentoComercial;
    private final CotizacionPdfService  cotizacionPdf;

    public DocumentoComercialController(DocumentoComercialService documentoComercial, CotizacionPdfService cotizacionPdf) {
        this.documentoComercial = documentoComercial;
        this.cotizacionPdf = cotizacionPdf;
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

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generarPdf(@PathVariable Integer id) throws IOException, DocumentException {
        DocumentoComercialDto documento = documentoComercial.getById(id)
                .orElseThrow(() -> new RuntimeException("Documento comercial no encontrado"));

        byte[] pdfBytes = cotizacionPdf.generarCotizacion(documento);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=cotizacion-" + documento.numeroFactura() + " .pdf")
                .body(pdfBytes);
    }
}
