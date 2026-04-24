package com.empaques.desa.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "material")
public class MaterialEntity extends  BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material")
    private Integer idMaterial;

    @Column(name = "nombre")
    private String name;

    @Column(name = "unidadMedida")
    private String unitMeasurement;

    private BigDecimal costoUnitario;

    @Column(name = "stockActual")
    private BigDecimal stockCurrent;
}
