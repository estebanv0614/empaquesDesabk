package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.PedidoDto;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository {
    List<PedidoDto> getAll();
    Optional<PedidoDto> getById(Integer id);
    PedidoDto save(PedidoDto dto);
    Optional<PedidoDto> update(Integer id, PedidoDto dto);
    Optional<PedidoDto> updateEstado(Integer id, Integer idEstado);
    boolean delete(Integer id);
    List<PedidoDto> getByClientId(Integer idClient);


}
