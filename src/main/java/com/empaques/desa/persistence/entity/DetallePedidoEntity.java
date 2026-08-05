package com.empaques.desa.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "detalle_pedido")
public class DetallePedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_pedido")
    private Integer idDetallePedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private PedidoEntity pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bolsa")
    private BolsaEntity bolsa;

    private Integer cantidad;

    @Column(name = "precio_unitario_venta")
    private BigDecimal precioUnitarioVenta;

    @Column(name = "subtotal_linea")
    private BigDecimal subtotalLinea;
}
