package com.empaques.desa.web.controller;

import com.empaques.desa.persistence.crud.CrudClientEntity;
import com.empaques.desa.persistence.crud.CrudUserEntity;
import com.empaques.desa.persistence.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final CrudUserEntity crudUserEntity;

    public UserController(CrudUserEntity crudUserEntity) {
        this.crudUserEntity = crudUserEntity;
    }

    @GetMapping("/users")
    public List<UserEntity> getAll() {
        return (List<UserEntity>) crudUserEntity.findAll();
    }
}
