package com.empaques.desa.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "persona")
public class PersonEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Integer idPersona;

    @Column(name = "nombre")
    private String name;

    @Column(name = "telefono")
    private String phone;

    @Column(name = "email", unique = true)
    private String mail;

    @Column(name = "direccion")
    private String aaddress;

}
