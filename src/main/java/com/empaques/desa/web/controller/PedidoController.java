package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.ClientDto;
import com.empaques.desa.domain.dto.PedidoDto;
import com.empaques.desa.domain.dto.UserDto;
import com.empaques.desa.domain.service.ClientService;
import com.empaques.desa.domain.service.PedidoService;
import com.empaques.desa.domain.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;
    private final UserService userService;
    private final ClientService clientService;

    public PedidoController(PedidoService pedidoService, UserService userService, ClientService clientService) {
        this.pedidoService = pedidoService;
        this.userService = userService;
        this.clientService = clientService;
    }

    @GetMapping
    public List<PedidoDto> getAll() {
        return pedidoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> getById(@PathVariable Integer id) {
        return pedidoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mis-pedidos")
    public ResponseEntity<List<PedidoDto>> getMisPedidos(Authentication authentication) {
        String username = authentication.getName();

        UserDto user = userService.getByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ClientDto client = clientService.getByPersonId(user.person().id())
                .orElseThrow(() -> new RuntimeException("Este usuario no tiene un cliente asociado"));

        return ResponseEntity.ok(pedidoService.getByClientId(client.id()));
    }

    @PostMapping
    public ResponseEntity<PedidoDto> save(@RequestBody PedidoDto dto) {
        return ResponseEntity.ok(pedidoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDto> update(@PathVariable Integer id, @RequestBody PedidoDto dto) {
        return pedidoService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoDto> updateEstado(@PathVariable Integer id, @RequestBody Map<String, Integer> body) {
        Integer idEstado = body.get("idEstado");
        return pedidoService.updateEstado(id, idEstado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = pedidoService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
