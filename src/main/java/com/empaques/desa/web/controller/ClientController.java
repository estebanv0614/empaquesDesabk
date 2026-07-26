package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.ClientDto;
import com.empaques.desa.domain.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientDto> getAll() {
        return clientService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable Integer id) {
        return clientService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClientDto> create(@RequestBody ClientDto dto) {
        return ResponseEntity.ok(clientService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable Integer id, @RequestBody ClientDto dto) {
        return clientService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = clientService.delete(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-person/{idPerson}")
    public ResponseEntity<ClientDto> getByPersonId(@PathVariable Integer idPerson) {
        return clientService.getByPersonId(idPerson)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
