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
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "pago_nomina")
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE pago_nomina SET deleted_at = CURRENT_TIMESTAMP WHERE id_pago = ?")
@SQLRestriction("deleted_at IS NULL")
public class PagoNominaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nomina")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado")
    private EmployeeEntity employee;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "salario_base")
    private BigDecimal salarioBase;

    private BigDecimal bonificaciones = BigDecimal.ZERO;
    private BigDecimal deducciones = BigDecimal.ZERO;

    @Column(name = "total_pagar")
    private BigDecimal totalPagar;

    private Boolean pagado = false;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_metodo_pago")
    private MetodoPagoEntity metodoPago;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gastos_operativos")
    private GastoOperativoEntity gasto;

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
    }
}
