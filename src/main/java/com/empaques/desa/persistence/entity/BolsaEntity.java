package com.empaques.desa.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "bolsa")
public class BolsaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bolsa")
    private Integer idBolsa;

    @Column(name = "tipo")
    private String tipoBolsa;

    @Column(name = "ancho_cm")
    private BigDecimal anchoCm;

    @Column(name = "largo_cm")
    private BigDecimal largoCm;

    @Column(name = "calibre")
    private BigDecimal caliber;

    @Column(name = "precio_base")
    private BigDecimal priceBase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado")
    private StateEntity state;
}
