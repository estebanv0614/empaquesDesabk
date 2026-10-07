package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.MaterialFaltanteDto;
import com.empaques.desa.domain.dto.OrdenProduccionDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrdenProduccionRepository {
    List<OrdenProduccionDto> getAll();
    Optional<OrdenProduccionDto> getById(Integer id);
    OrdenProduccionDto save(OrdenProduccionDto dto);
    Optional<OrdenProduccionDto> update(Integer id, OrdenProduccionDto dto);
    boolean delete(Integer id);
    OrdenProduccionDto crearDesdeDetalle(Integer idDetallePedido, Integer idUsuario);
    List<MaterialFaltanteDto> validarMaterial (Integer idOrden);
    OrdenProduccionDto iniciar (Integer idOrden);
    OrdenProduccionDto finalizar (Integer idOrden, BigDecimal producida, BigDecimal merma,
                                  String observacion, Integer idUsuario);
}
