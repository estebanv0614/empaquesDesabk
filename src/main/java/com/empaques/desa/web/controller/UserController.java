package com.empaques.desa.web.controller;


import com.empaques.desa.domain.dto.UserDto;
import com.empaques.desa.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Integer id) {
        return userService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserDto dto) {
        return ResponseEntity.ok(userService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Integer id, @RequestBody @Valid UserDto dto) {
        return userService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = userService.delete(id);
        if (!userService.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivate(@PathVariable Integer id) {
        boolean response = userService.deactivate(id);
        if (!response) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "message",
                            "Usuario no encontrado"));
        }
        return ResponseEntity.ok(Map.of(
                "message",
                "Usuario desactivado correctamente"
        ));
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<?> activate(@PathVariable Integer id) {
        boolean response = userService.activate(id);
        if (!response) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "message",
                            "Usuario no encontrado"
                    ));
        }
        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Usuario activado correctamente"
                )
        );
    }
}
