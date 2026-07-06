package com.empaques.desa.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RecetaBolsaPK implements Serializable {

    @Column(name = "id_bolsa")
    private Integer idBolsa;

    @Column(name = "id_material")
    private Integer idMaterial;

    public RecetaBolsaPK() {
    }

    public RecetaBolsaPK(Integer idBolsa, Integer idMaterial) {
        this.idBolsa = idBolsa;
        this.idMaterial = idMaterial;
    }

    public Integer getIdBolsa() {
        return idBolsa;
    }

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdBolsa(Integer idBolsa) {
        this.idBolsa = idBolsa;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof RecetaBolsaPK that)) return false;
        return Objects.equals(idBolsa, that.idBolsa)
                && Objects.equals(idMaterial, that.idMaterial);
    }
    public int hashCode() {
        return Objects.hash(idBolsa, idMaterial);
    }
}
