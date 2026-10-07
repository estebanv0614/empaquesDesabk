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
}
