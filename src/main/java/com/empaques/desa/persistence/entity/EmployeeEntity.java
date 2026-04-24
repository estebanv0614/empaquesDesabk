package com.empaques.desa.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "empleado")
@Getter
@Setter
public class EmployeeEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer idEmployee;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", nullable = false, unique = true)
    private PersonEntity person;

    @Column(name = "cargo")
    private String position;

    @Column(name = "salario")
    private BigDecimal salary;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado")
    private StateEntity state;
}
