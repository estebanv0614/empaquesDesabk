package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.GastoOperativoDto;
import com.empaques.desa.domain.dto.GastoOperativoForm;
import com.empaques.desa.domain.service.GastoOperativoService;
import com.empaques.desa.domain.service.ImagenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/gastos-operativos")
public class GastoOperativoController {
    private final GastoOperativoService gastoOperativo;
    private final ImagenService imagen;

    public GastoOperativoController(GastoOperativoService gastoOperativo, ImagenService imagen) {
        this.gastoOperativo = gastoOperativo;
        this.imagen = imagen;
    }

    @GetMapping
    public List<GastoOperativoDto> getAll() {
        return gastoOperativo.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GastoOperativoDto> getById(@PathVariable Integer id) {
        return gastoOperativo.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  ResponseEntity<GastoOperativoDto> create (@Valid @ModelAttribute GastoOperativoForm form) throws IOException {
        String evidenciaUrl = imagen.guardar(form.evidencia(), "gastos");
        GastoOperativoDto creado = gastoOperativo.save(form.toRequest(), evidenciaUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = gastoOperativo.delete(id);
        if (deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
