package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.PedidoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CrudPedidoEntity extends CrudRepository<PedidoEntity, Integer> {
    List<PedidoEntity> findByClient_IdClient(Integer idClient);

    @Query(value = "SELECT COUNT(*) FROM pedido WHERE deleted_at IS NULL AND DATE(fecha_pedido) = CURDATE()", nativeQuery = true)
    long countHoy();

    @Query(value = "SELECT COUNT(*) FROM pedido WHERE deleted_at IS NULL AND YEARWEEK(fecha_pedido, 3) = YEARWEEK(CURDATE(), 3)", nativeQuery = true)
    long countEstaSemana();

    @Query(value = "SELECT COUNT(*) FROM pedido WHERE deleted_at IS NULL AND YEAR(fecha_pedido) = YEAR(CURDATE()) AND MONTH(fecha_pedido) = MONTH(CURDATE())", nativeQuery = true)
    long countEsteMes();

    @Query(value = "SELECT COUNT(*) FROM pedido WHERE deleted_at IS NULL AND YEAR(fecha_pedido) = YEAR(CURDATE())", nativeQuery = true)
    long countEsteAnio();

    // ===== Desglose histórico (para gráficos) =====
    @Query(value = "SELECT DATE_FORMAT(fecha_pedido, '%Y-%m') AS periodo, COUNT(*) AS cantidad " +
            "FROM pedido WHERE deleted_at IS NULL " +
            "GROUP BY DATE_FORMAT(fecha_pedido, '%Y-%m') " +
            "ORDER BY periodo ASC LIMIT 12", nativeQuery = true)
    List<Object[]> countPorMes();

    @Query(value = "SELECT DATE(fecha_pedido) AS periodo, COUNT(*) AS cantidad " +
            "FROM pedido WHERE deleted_at IS NULL AND fecha_pedido >= CURDATE() - INTERVAL 30 DAY " +
            "GROUP BY DATE(fecha_pedido) " +
            "ORDER BY periodo ASC", nativeQuery = true)
    List<Object[]> countPorDia();
}
