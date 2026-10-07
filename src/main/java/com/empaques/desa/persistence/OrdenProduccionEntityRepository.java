package com.empaques.desa.persistence;

import com.empaques.desa.domain.TipoMovimiento;
import com.empaques.desa.domain.dto.MaterialFaltanteDto;
import com.empaques.desa.domain.dto.OrdenProduccionDto;
import com.empaques.desa.domain.repository.OrdenProduccionRepository;
import com.empaques.desa.persistence.crud.*;
import com.empaques.desa.persistence.entity.*;
import com.empaques.desa.persistence.mapper.OrdenProduccionMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrdenProduccionEntityRepository implements OrdenProduccionRepository {
    private static final Integer ID_ESTADO_PENDIENTE = 2;
    private static final Integer ID_ESTADO_EN_PRODUCCION = 12;
    private static final Integer ID_ESTADO_FINALIZADA = 13;
    private static final Integer ID_ESTADO_ENTREGADO = 10;
    private static final Integer ID_ESTADO_CANCELADO = 11;

    private final CrudOrdenProduccionEntity ordenProduccion;
    private final CrudEstadoEntity estadoEntity;
    private final OrdenProduccionMapper produccionMapper;
    private final CrudDetallePedidoEntity detallePedido;
    private final CrudUserEntity user;
    private final CrudRecetaBolsaEntity recetaBolsa;
    private final CrudMaterialEntity material;
    private final CrudMovimientoInventarioEntity movimientoInventario;
    private final CrudPedidoEntity pedidoCr;

    public OrdenProduccionEntityRepository(CrudOrdenProduccionEntity ordenProduccion, CrudEstadoEntity estadoEntity, OrdenProduccionMapper produccionMapper, CrudDetallePedidoEntity detallePedido, CrudUserEntity user, CrudRecetaBolsaEntity recetaBolsa, CrudMaterialEntity material, CrudMovimientoInventarioEntity movimientoInventario, CrudPedidoEntity pedidoCr) {
        this.ordenProduccion = ordenProduccion;
        this.estadoEntity = estadoEntity;
        this.produccionMapper = produccionMapper;
        this.detallePedido = detallePedido;
        this.user = user;
        this.recetaBolsa = recetaBolsa;
        this.material = material;
        this.movimientoInventario = movimientoInventario;
        this.pedidoCr = pedidoCr;
    }

    @Override
    public List<OrdenProduccionDto> getAll() {
        return produccionMapper.toDtoList(ordenProduccion.findAll());
    }

    @Override
    public Optional<OrdenProduccionDto> getById(Integer id) {
        return ordenProduccion.findById(id)
                .map(produccionMapper::toDto);
    }

    @Override
    public OrdenProduccionDto save(OrdenProduccionDto dto) {
        OrdenProduccionEntity entity = produccionMapper.toEntity(dto);
        entity.setDetallePedido(
                detallePedido.findById(dto.detallePedido().id())
                        .orElseThrow(() ->new RuntimeException("Detalle de pedido no encontrado " + dto.detallePedido().id()))
        );
        entity.setUser(
                user.findById(dto.user().id())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado " + dto.user().id()))
        );
        entity.setEstado(
                estadoEntity.findById(dto.estado().id())
                        .orElseThrow(() -> new RuntimeException("Estado no esta disponible " + dto.estado().id()))
        );
        return produccionMapper.toDto(ordenProduccion.save(entity));
    }

    @Override
    public Optional<OrdenProduccionDto> update(Integer id, OrdenProduccionDto dto) {
        return ordenProduccion.findById(id)
                .map(entity -> {
                    entity.setFechaProduccion(dto.fechaProduccion());
                    entity.setEstado(
                            estadoEntity.findById(dto.estado().id())
                                    .orElseThrow(() -> new RuntimeException("Estado no esta disponible " + dto.estado().id()))
                    );
                    OrdenProduccionEntity updated = ordenProduccion.save(entity);
                    return produccionMapper.toDto(updated);
                });
    }

    @Override
    public boolean delete(Integer id) {
        return ordenProduccion.findById(id)
                .map(entity -> {
                    ordenProduccion.save(entity);
                    return true;
                }).orElse(false);
    }

    @Override
    public OrdenProduccionDto crearDesdeDetalle(Integer idDetallePedido, Integer idUsuario) {
        DetallePedidoEntity detalle = detallePedido.findById(idDetallePedido)
                .orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado " + idDetallePedido));
        OrdenProduccionEntity orden = new OrdenProduccionEntity();
        orden.setDetallePedido(detalle);
        orden.setUser(user.findById(idUsuario)
                .orElseThrow(() ->new RuntimeException("Usuario no encontrado " + idUsuario)));
        orden.setEstado(estadoEntity.findById(ID_ESTADO_PENDIENTE)
                .orElseThrow(() -> new RuntimeException("Estado pendiente no encontrado")));
        orden.setCantidadProducir(BigDecimal.valueOf(detalle.getCantidad()));
        orden.setCantidadProducida(BigDecimal.ZERO);
        orden.setCantidadMerma(BigDecimal.ZERO);

        return produccionMapper.toDto(ordenProduccion.save(orden));
    }

    @Override
    public List<MaterialFaltanteDto> validarMaterial(Integer idOrden) {
        OrdenProduccionEntity orden = ordenProduccion.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada " + idOrden));
        Integer idBolsa = orden.getDetallePedido().getBolsa().getIdBolsa();
        List<MaterialFaltanteDto> faltantes = new ArrayList<>();

        for (RecetaBolsaEntity r : recetaBolsa.findByIdBolsa(idBolsa)) {
            MaterialEntity m = r.getMaterial();
            BigDecimal requerido = r.getCantidadRequerida().multiply(orden.getCantidadProducir());
            if (m.getStockActual().compareTo(requerido) < 0) {
                faltantes.add(new MaterialFaltanteDto(
                        m.getIdMaterial(), m.getName(), requerido, m.getStockActual(),
                        requerido.subtract(m.getStockActual())));
            }
        }
        return faltantes;
    }

    @Override
    public OrdenProduccionDto iniciar(Integer idOrden) {
        List<MaterialFaltanteDto> faltantes = validarMaterial(idOrden);
        if (!faltantes.isEmpty()) {
            throw  new IllegalStateException("Material insuficiente: " + faltantes);
        }
        OrdenProduccionEntity orden = ordenProduccion.findById(idOrden).orElseThrow();
        if (orden.getFechaFin() != null) {
            throw new IllegalStateException("La orden ya fue finalizada");
        }
        orden.setEstado(estadoEntity.findById(ID_ESTADO_EN_PRODUCCION)
                .orElseThrow(()-> new RuntimeException("Estado 'En producción' no encontrado")));
        orden.setFechaProduccion(LocalDateTime.now());
        OrdenProduccionEntity guardada = ordenProduccion.save(orden);
        sincronizarEstadoPedido(guardada);
        return produccionMapper.toDto(guardada);
    }

    @Override
    public OrdenProduccionDto finalizar(Integer idOrden, BigDecimal producida, BigDecimal merma, String observacion, Integer idUsuario) {
        OrdenProduccionEntity orden = ordenProduccion.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada " + idOrden));
        if (orden.getFechaFin() != null) {
            throw new IllegalStateException("La orden ya fue finalizada");
        }
        UserEntity usuario = user.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado " + idUsuario));
        BigDecimal unidadesTotales = producida.add(merma);
        Integer idBolsa = orden.getDetallePedido().getBolsa().getIdBolsa();
        for (RecetaBolsaEntity r : recetaBolsa.findByIdBolsa(idBolsa)) {
            MaterialEntity m = material.findByIdForUpdate(r.getMaterial().getIdMaterial())
                    .orElseThrow(() -> new RuntimeException("Material no encontrado"));
            BigDecimal consumo = r.getCantidadRequerida().multiply(unidadesTotales);
            BigDecimal antes = m.getStockActual();
            if (antes.compareTo(consumo) < 0) {
                throw new IllegalStateException("Stock insuficiente de " + m.getName()
                    + ": se necesitan " + consumo + " y has " + antes);
            }
            BigDecimal despues = antes.subtract(consumo);
            m.setStockActual(despues);
            material.save(m);

            MovimientoInventarioEntity mov = new MovimientoInventarioEntity();
            mov.setMaterial(m);
            mov.setOrdenProduccion(orden);
            mov.setUser(usuario);
            mov.setTipoMovimiento(TipoMovimiento.PRODUCCION);
            mov.setCantidad(consumo);
            mov.setStockAntes(antes);
            mov.setStockDespues(despues);
            mov.setCostoUnitario(m.getCostoUnitario());
            mov.setReferencia("OP-" + orden.getIdOrden());
            movimientoInventario.save(mov);
        }
        orden.setCantidadProducida(producida);
        orden.setCantidadMerma(merma);
        orden.setObservacion(observacion);
        orden.setFechaFin(LocalDateTime.now());
        orden.setEstado(estadoEntity.findById(ID_ESTADO_FINALIZADA)
                .orElseThrow(() -> new RuntimeException("Estado 'Finalizada' no encontrado")));
        OrdenProduccionEntity guardada = ordenProduccion.save(orden);
        sincronizarEstadoPedido(guardada);
        return produccionMapper.toDto(guardada);
    }

    private void sincronizarEstadoPedido(OrdenProduccionEntity orden) {
        PedidoEntity pedido = orden.getDetallePedido().getPedido();
        Integer estadoActual = pedido.getEstado().getIdState();

        if (ID_ESTADO_CANCELADO.equals(estadoActual) || ID_ESTADO_ENTREGADO.equals(estadoActual)) {
            return;
        }

        List<OrdenProduccionEntity> ordenes =
                ordenProduccion.findByDetallePedido_Pedido_IdPedido(pedido.getIdPedido());

        boolean todasFinalizadas = !ordenes.isEmpty()
                && ordenes.stream().allMatch(o -> o.getFechaFin() != null);

        Integer nuevoEstado = todasFinalizadas ? ID_ESTADO_FINALIZADA : ID_ESTADO_EN_PRODUCCION;

        if (!nuevoEstado.equals(estadoActual)) {
            pedido.setEstado(estadoEntity.findById(nuevoEstado)
                    .orElseThrow(() -> new RuntimeException("Estado no encontrado " + nuevoEstado)));
            pedidoCr.save(pedido);
        }
    }
}
