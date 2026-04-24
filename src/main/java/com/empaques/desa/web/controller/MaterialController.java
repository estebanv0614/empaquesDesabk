package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.MaterialDto;
import com.empaques.desa.domain.service.MaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materials")
public class MaterialController {
    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public List<MaterialDto> getAll(){
        return materialService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDto> getById(@PathVariable Integer id) {
        return materialService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MaterialDto> create(@RequestBody MaterialDto dto){
        return ResponseEntity.ok(materialService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialDto> update(@PathVariable Integer id, @RequestBody MaterialDto dto){
        return materialService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        materialService.delete(id);
        return ResponseEntity.ok().build();
    }
}
