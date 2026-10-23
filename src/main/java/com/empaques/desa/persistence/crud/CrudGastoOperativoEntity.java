package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.GastoOperativoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CrudGastoOperativoEntity extends CrudRepository <GastoOperativoEntity, Integer> {
    List<GastoOperativoEntity> findByFechaGastoBetween(LocalDate desde, LocalDate hasta);

    @Query(value = """
    SELECT monto / cantidad FROM gasto_operativo
    WHERE id_material = :id AND cantidad > 0 AND deleted_at IS NULL
    ORDER BY fecha_gasto DESC, id_gastos_operativos DESC
    LIMIT 1
    """, nativeQuery = true)
    Optional<BigDecimal> findUltimoCosto(@Param("id") Integer id);
}
