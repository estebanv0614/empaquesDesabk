package com.empaques.desa.persistence.entity;

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
@Table(name = "orden_produccion")
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE orden_produccion SET deleted_at = CURRENT_TIMESTAMP WHERE id_orden = ?")
@SQLRestriction("deleted_at IS NULL")
public class OrdenProduccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Integer idOrden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_detalle_pedido", nullable = false)
    private DetallePedidoEntity detallePedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UserEntity user;

    @Column(name = "cantidad_producir")
    private BigDecimal cantidadProducir;

    @Column(name = "cantidad_producida")
    private BigDecimal cantidadProducida = BigDecimal.ZERO;

    @Column(name = "cantidad_merma")
    private BigDecimal cantidadMerma = BigDecimal.ZERO;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    private String observacion;

    @Column(name = "fecha_produccion")
    private LocalDateTime fechaProduccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado")
    private EstadoEntity estado;

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
    }
}
