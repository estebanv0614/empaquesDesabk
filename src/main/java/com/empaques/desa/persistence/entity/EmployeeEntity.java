package com.empaques.desa.persistence.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "empleado")
@Data
@Builder
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(name = "nombre")
    private String name;

    private String cargo;

    @Column(name = "salario")
    private BigDecimal salary;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    private String estado;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private UserEntity usuario;
}
