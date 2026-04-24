package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.StateDto;
import com.empaques.desa.domain.service.StateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/state")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public List<StateDto> getAll() {
        return stateService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateDto> getById(@PathVariable Integer id) {
        return stateService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StateDto> create(@RequestBody StateDto dto) {
        return ResponseEntity.ok(stateService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StateDto> update(
            @PathVariable Integer id,
            @RequestBody StateDto dto) {
        return stateService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        this.stateService.delete(id);
        return ResponseEntity.ok().build();
    }
}
