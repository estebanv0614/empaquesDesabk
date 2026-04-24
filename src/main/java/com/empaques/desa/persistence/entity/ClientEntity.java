package com.empaques.desa.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Setter
@Table(name = "cliente")
@SQLDelete(sql = "UPDATE cliente SET deleted_at = NOW() WHERE id_cliente = ?")
public class ClientEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idClient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private PersonEntity person;

    @Column(name = "empresa")
    private String company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado")
    private StateEntity state;
}
