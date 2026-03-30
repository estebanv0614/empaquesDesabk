package com.empaques.desa.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cliente")
@Data
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idClient;

    @Column(name = "nombre")
    private String nome;

    private String empresa;

    @Column(name = "telefono")
    private String phone;

    private String email;

    @Column(name = "direccion")
    private String address;

    private String estado;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private UserEntity usuario;

}
