package com.empaques.desa.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Table(name = "rol")
@Data
public class RolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;

    private String nombre;

    @ManyToMany(mappedBy = "rol")
    private Set<UserEntity> usuario;
}
