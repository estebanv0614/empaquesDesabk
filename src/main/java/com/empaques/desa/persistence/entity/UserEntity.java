package com.empaques.desa.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "usuario")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    private String unsername;

    private String password;

    private Boolean activo;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaRegistro;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private Set<RolEntity> rol;

    @OneToOne(mappedBy = "usuario")
    private EmployeeEntity employee;

    @OneToOne(mappedBy = "usuario")
    private ClientEntity client;
}