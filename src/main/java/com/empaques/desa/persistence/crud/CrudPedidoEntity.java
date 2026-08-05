package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.PedidoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CrudPedidoEntity extends CrudRepository<PedidoEntity, Integer> {
    List<PedidoEntity> findByClient_IdClient(Integer idClient);
}
