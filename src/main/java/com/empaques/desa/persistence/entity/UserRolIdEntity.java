package com.empaques.desa.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserRolIdEntity implements Serializable {
    @Column(name = "id_usuario")
    private Integer userId;

    @Column(name = "id_rol")
    private Integer rolId;
}
