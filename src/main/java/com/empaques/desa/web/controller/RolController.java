package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.RolDto;
import com.empaques.desa.persistence.crud.CrudRolEntity;
import com.empaques.desa.persistence.mapper.RolMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolController {
    private final CrudRolEntity rol;
    private final RolMapper rolMapper;

    public RolController(CrudRolEntity rol, RolMapper rolMapper) {
        this.rol = rol;
        this.rolMapper = rolMapper;
    }

    @GetMapping
    public List<RolDto> getAll() {
        return rolMapper.toDtoList(rol.findAll());
    }
}
