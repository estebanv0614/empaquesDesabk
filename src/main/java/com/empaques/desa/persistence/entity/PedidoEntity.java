package com.empaques.desa.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "pedido")
@SQLDelete(sql = "UPDATE pedido SET deleted_at = CURRENT_TIMESTAMP WHERE id_pedido = ?")
@SQLRestriction("deleted_at IS NULL")
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @Column(name = "numero_pedido")
    private String numeroPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private ClientEntity client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario",  nullable = false)
    private UserEntity userVendedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado")
    private EstadoEntity estado;

    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;

    @Column(name = "fecha_entrega_estimada")
    private LocalDate fechaEntregaEstimada;

    private BigDecimal subtotal;

    private BigDecimal impuesto;

    private  BigDecimal total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_metodo_pago")
    private MetodoPagoEntity metodoPago;

    private String observacion;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetallePedidoEntity> detallePedidos;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.fechaPedido == null) {
            this.fechaPedido = LocalDateTime.now();
        }
        if (this.impuesto == null) {
            this.impuesto = BigDecimal.ZERO;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
