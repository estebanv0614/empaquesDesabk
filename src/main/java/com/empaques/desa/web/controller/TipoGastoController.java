package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.TipoGastoDto;
import com.empaques.desa.domain.service.TipoGastoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipo-gasto")
public class TipoGastoController {
    private final TipoGastoService tipoGastoService;

    public TipoGastoController(TipoGastoService tipoGastoService) {
        this.tipoGastoService = tipoGastoService;
    }

    @GetMapping
    public List<TipoGastoDto> getAll() {
        return tipoGastoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoGastoDto> getById(@PathVariable Integer id) {
        return tipoGastoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoGastoDto> create(@RequestBody TipoGastoDto dto){
        return ResponseEntity.ok(tipoGastoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoGastoDto> update(@PathVariable Integer id, @RequestBody TipoGastoDto dto) {
        return tipoGastoService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        this.tipoGastoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
