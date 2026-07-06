package com.empaques.desa.persistence.entity;

import com.empaques.desa.domain.MovimientoBolsa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "movimiento_bolsa")
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE movimiento_bolsa SET deleted_at = CURRENT_TIMESTAMP WHERE id_movimiento_bolsa = ?")
@SQLRestriction("deleted_at IS NULL")
public class MovimientoBolsaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento_bolsa")
    private Integer idMovimientoBolsa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_material")
    private MaterialEntity material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orden")
    private OrdenProduccionEntity ordenProduccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "movimiento_bolsa")
    private MovimientoBolsa movimientoBolsa;

    private BigDecimal cantidad;

    @Column(name = "stock_antes")
    private BigDecimal stockAntes;

    @Column(name = "stock_despues")
    private BigDecimal stockDespues;

    @Column(name = "fecha_movimiento")
    private LocalDateTime fechaMovimiento;

    private String referencia;

    private String observacion;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_modified_at")
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    @Column(name = "created_by", updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "last_modified_by")
    @LastModifiedBy
    private String lastModifiedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.fechaMovimiento = LocalDateTime.now();
    }
}
