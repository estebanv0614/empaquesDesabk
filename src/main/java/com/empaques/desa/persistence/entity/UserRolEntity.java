package com.empaques.desa.persistence.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "usuario_rol")
public class UserRolEntity {
    @EmbeddedId
    private UserRolIdEntity id;

    @ManyToOne
    @MapsId("userId")
    private UserEntity usuario;

    @ManyToOne
    @MapsId("rolId")
    private RolEntity rol;

}
