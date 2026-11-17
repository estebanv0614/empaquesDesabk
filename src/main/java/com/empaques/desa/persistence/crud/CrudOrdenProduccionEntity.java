package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.OrdenProduccionEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CrudOrdenProduccionEntity extends CrudRepository<OrdenProduccionEntity, Integer> {
    List<OrdenProduccionEntity> findByDetallePedido_Pedido_IdPedido(Integer idPedido);

    @Query("""
            SELECT SUM(r.cantidadRequerida * o.cantidadProducir)
            FROM OrdenProduccionEntity o, RecetaBolsaEntity r
            WHERE r.bolsa.idBolsa = o.detallePedido.bolsa.idBolsa
              AND r.material.idMaterial = :idMaterial
              AND o.fechaProduccion IS NOT NULL
              AND o.fechaFin IS NULL
              AND o.idOrden <> :idOrden
            """)

    BigDecimal materialComprometido(@Param("idMaterial") Integer idMaterial,
                                    @Param("idOrden") Integer idOrden);
}
