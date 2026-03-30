package com.empaques.desa.web.controller;


import com.empaques.desa.persistence.crud.CrudClientEntity;
import com.empaques.desa.persistence.entity.ClientEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {
    private final CrudClientEntity crudClientEntity;

    public ClientController(CrudClientEntity crudClientEntity) {
        this.crudClientEntity = crudClientEntity;
    }

    @GetMapping("/clients")
    public List<ClientEntity> getAll() {
        return (List<ClientEntity>) this.crudClientEntity.findAll();
    }
}
