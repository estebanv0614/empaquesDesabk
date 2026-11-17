package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.*;
import com.empaques.desa.domain.service.OrdenProduccionService;
import com.empaques.desa.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.Authenticator;
import java.util.List;

@RestController
@RequestMapping("/ordenes-produccion")
public class OrdenProduccionController {
    private final OrdenProduccionService produccionService;
    private final UserService userSer;

    public OrdenProduccionController(OrdenProduccionService produccionService, UserService userSer) {
        this.produccionService = produccionService;
        this.userSer = userSer;
    }

    public record FinalizarOrdenRequest(BigDecimal cantidadProducida, BigDecimal cantidadMerma,
                                        String observacion, List<ConsumoMaterialDto> consumos) {}

    @GetMapping
    public List<OrdenProduccionDto> getAll() {
        return produccionService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenProduccionDto> getById(@PathVariable Integer id) {
        return produccionService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrdenProduccionDto> create(@RequestBody OrdenProduccionDto dto) {
        return ResponseEntity.ok(produccionService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenProduccionDto> update(@PathVariable Integer id, @RequestBody OrdenProduccionDto dto) {
        return produccionService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = produccionService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/validar-material")
    public List<MaterialFaltanteDto> validarMaterial(@PathVariable Integer id) {
        return produccionService.validarMaterial(id);
    }

    /** Materiales de la receta con la cantidad calculada para (producida + merma). */
    @GetMapping("/{id}/consumo-sugerido")
    public List<ConsumoSugeridoDto> consumoSugerido(@PathVariable Integer id,
                                                    @RequestParam BigDecimal producida,
                                                    @RequestParam(defaultValue = "0") BigDecimal merma) {
        return produccionService.consumoSugerido(id, producida, merma);
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<OrdenProduccionDto> iniciar(@PathVariable Integer id) {
        return ResponseEntity.ok(produccionService.iniciar(id));
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<OrdenProduccionDto> finalizar(@PathVariable Integer id,
                                                        @RequestBody FinalizarOrdenRequest body,
                                                        Authentication authentication) {
        UserDto user = userSer.getByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(produccionService.finalizar(
                id, body.cantidadProducida(), body.cantidadMerma(), body.observacion(),
                user.id(), body.consumos()));
    }
}
