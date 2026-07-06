package com.empaques.desa.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "receta_bolsa")
public class RecetaBolsaEntity {
    @EmbeddedId
    private RecetaBolsaPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idBolsa")
    @JoinColumn(name = "id_bolsa")
    private BolsaEntity bolsa;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idMaterial")
    @JoinColumn(name = "id_material")
    private MaterialEntity material;

    @Column(name = "cantidad_requerida")
    private BigDecimal cantidadRequerida;
}
