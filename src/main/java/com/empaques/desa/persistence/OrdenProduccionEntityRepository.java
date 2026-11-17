package com.empaques.desa.persistence;

import com.empaques.desa.domain.TipoMovimiento;
import com.empaques.desa.domain.dto.ConsumoMaterialDto;
import com.empaques.desa.domain.dto.ConsumoSugeridoDto;
import com.empaques.desa.domain.dto.MaterialFaltanteDto;
import com.empaques.desa.domain.dto.OrdenProduccionDto;
import com.empaques.desa.domain.repository.OrdenProduccionRepository;
import com.empaques.desa.persistence.crud.*;
import com.empaques.desa.persistence.entity.*;
import com.empaques.desa.persistence.mapper.OrdenProduccionMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class OrdenProduccionEntityRepository implements OrdenProduccionRepository {
    private static final Integer ID_ESTADO_PENDIENTE = 3;
    private static final Integer ID_ESTADO_EN_PRODUCCION = 7;
    private static final Integer ID_ESTADO_FINALIZADA = 8;
    private static final Integer ID_ESTADO_ENTREGADO = 5;
    private static final Integer ID_ESTADO_CANCELADO = 4;

    private static final String CATEGORIA_INTERCAMBIABLE = "TINTA";

    private final CrudOrdenProduccionEntity ordenProduccion;
    private final CrudEstadoEntity estadoEntity;
    private final OrdenProduccionMapper produccionMapper;
    private final CrudDetallePedidoEntity detallePedido;
    private final CrudUserEntity user;
    private final CrudRecetaBolsaEntity recetaBolsa;
    private final CrudMaterialEntity material;
    private final CrudMovimientoInventarioEntity movimientoInventario;
    private final CrudPedidoEntity pedidoCr;
    private final CrudGastoOperativoEntity gastoCr;

    public OrdenProduccionEntityRepository(CrudOrdenProduccionEntity ordenProduccion, CrudEstadoEntity estadoEntity, OrdenProduccionMapper produccionMapper, CrudDetallePedidoEntity detallePedido, CrudUserEntity user, CrudRecetaBolsaEntity recetaBolsa, CrudMaterialEntity material, CrudMovimientoInventarioEntity movimientoInventario, CrudPedidoEntity pedidoCr, CrudGastoOperativoEntity gastoCr) {
        this.ordenProduccion = ordenProduccion;
        this.estadoEntity = estadoEntity;
        this.produccionMapper = produccionMapper;
        this.detallePedido = detallePedido;
        this.user = user;
        this.recetaBolsa = recetaBolsa;
        this.material = material;
        this.movimientoInventario = movimientoInventario;
        this.pedidoCr = pedidoCr;
        this.gastoCr = gastoCr;
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
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado " + idUsuario)));
        orden.setEstado(estadoEntity.findById(ID_ESTADO_PENDIENTE)
                .orElseThrow(() -> new RuntimeException("Estado pendiente no encontrado")));
        orden.setCantidadProducir(BigDecimal.valueOf(detalle.getCantidad()));
        orden.setCantidadProducida(BigDecimal.ZERO);
        orden.setCantidadMerma(BigDecimal.ZERO);

        return produccionMapper.toDto(ordenProduccion.save(orden));
    }

    /** Vista previa (sin bloqueos): qué material faltaría si se iniciara la orden ahora. */
    @Override
    public List<MaterialFaltanteDto> validarMaterial(Integer idOrden) {
        OrdenProduccionEntity orden = ordenProduccion.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada " + idOrden));
        List<RecetaBolsaEntity> receta = recetaBolsa.findByIdBolsa(
                        orden.getDetallePedido().getBolsa().getIdBolsa())
                .stream().filter(r -> !esIntercambiable(r.getMaterial())).toList();
        return calcularFaltantes(orden, receta, null);
    }

    @Override
    public OrdenProduccionDto iniciar(Integer idOrden) {
        OrdenProduccionEntity orden = ordenProduccion.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada " + idOrden));

        if (orden.getFechaFin() != null) {
            throw new IllegalStateException("La orden ya fue finalizada");
        }
        if (!ID_ESTADO_PENDIENTE.equals(orden.getEstado().getIdState())) {
            throw new IllegalStateException("Solo se puede iniciar una orden en estado pendiente");
        }

        List<RecetaBolsaEntity> receta = recetaBolsa.findByIdBolsa(
                orden.getDetallePedido().getBolsa().getIdBolsa());
        if (receta.isEmpty()) {
            throw new IllegalStateException("La bolsa de esta orden no tiene receta definida");
        }

        // Las tintas se validan al finalizar, cuando se sabe el color realmente usado
        List<RecetaBolsaEntity> aValidar = receta.stream()
                .filter(r -> !esIntercambiable(r.getMaterial())).toList();

        // Bloquear los materiales en orden de id (evita deadlocks y que dos inicios validen a la vez)
        Map<Integer, MaterialEntity> bloqueados = new HashMap<>();
        aValidar.stream()
                .map(r -> r.getMaterial().getIdMaterial())
                .sorted()
                .forEach(idMat -> bloqueados.put(idMat,
                        material.findByIdForUpdate(idMat)
                                .orElseThrow(() -> new RuntimeException("Material no encontrado " + idMat))));

        List<MaterialFaltanteDto> faltantes = calcularFaltantes(orden, aValidar, bloqueados);
        if (!faltantes.isEmpty()) {
            throw new IllegalStateException("Material insuficiente: " + faltantes);
        }

        orden.setEstado(estadoEntity.findById(ID_ESTADO_EN_PRODUCCION)
                .orElseThrow(() -> new RuntimeException("Estado 'En producción' no encontrado")));
        orden.setFechaProduccion(LocalDateTime.now());
        OrdenProduccionEntity guardada = ordenProduccion.save(orden);
        sincronizarEstadoPedido(guardada);
        return produccionMapper.toDto(guardada);
    }

    @Override
    public OrdenProduccionDto finalizar(Integer idOrden, BigDecimal producida, BigDecimal merma,
                                        String observacion, Integer idUsuario,
                                        List<ConsumoMaterialDto> consumos) {
        OrdenProduccionEntity orden = ordenProduccion.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada " + idOrden));
        if (orden.getFechaFin() != null) {
            throw new IllegalStateException("La orden ya fue finalizada");
        }
        UserEntity usuario = user.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado " + idUsuario));
        BigDecimal unidadesTotales = producida.add(merma);
        Integer idBolsa = orden.getDetallePedido().getBolsa().getIdBolsa();

        // TreeMap: se recorre por id de material, el mismo orden de bloqueo que en iniciar
        Map<Integer, BigDecimal> aConsumir = new TreeMap<>();
        if (consumos == null || consumos.isEmpty()) {
            for (RecetaBolsaEntity r : recetaBolsa.findByIdBolsa(idBolsa)) {
                aConsumir.merge(r.getMaterial().getIdMaterial(),
                        r.getCantidadRequerida().multiply(unidadesTotales), BigDecimal::add);
            }
        } else {
            for (ConsumoMaterialDto c : consumos) {
                if (c.idMaterial() == null || c.cantidad() == null || c.cantidad().signum() <= 0) {
                    throw new IllegalStateException(
                            "Cada consumo necesita un material y una cantidad mayor que cero");
                }
                aConsumir.merge(c.idMaterial(), c.cantidad(), BigDecimal::add);
            }
        }
        if (aConsumir.isEmpty()) {
            throw new IllegalStateException("La orden no tiene materiales por descontar");
        }

        for (Map.Entry<Integer, BigDecimal> e : aConsumir.entrySet()) {
            MaterialEntity m = material.findByIdForUpdate(e.getKey())
                    .orElseThrow(() -> new RuntimeException("Material no encontrado " + e.getKey()));
            BigDecimal consumo = e.getValue();
            BigDecimal antes = m.getStockActual();
            if (antes.compareTo(consumo) < 0) {
                throw new IllegalStateException("Stock insuficiente de " + m.getName()
                        + ": se necesitan " + consumo + " y hay " + antes);
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
            mov.setReferencia("OP-" + orden.getIdOrden());
            mov.setCostoUnitario(gastoCr.findUltimoCosto(m.getIdMaterial()).orElse(null));
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

    /** Materiales de la receta con la cantidad calculada para (producida + merma). */
    @Override
    public List<ConsumoSugeridoDto> consumoSugerido(Integer idOrden, BigDecimal producida, BigDecimal merma) {
        OrdenProduccionEntity orden = ordenProduccion.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada " + idOrden));
        BigDecimal total = producida.add(merma);
        return recetaBolsa.findByIdBolsa(orden.getDetallePedido().getBolsa().getIdBolsa())
                .stream()
                .map(r -> new ConsumoSugeridoDto(
                        r.getMaterial().getIdMaterial(),
                        r.getMaterial().getName(),
                        r.getMaterial().getUnidadMedida(),
                        r.getCantidadRequerida().multiply(total)))
                .toList();
    }

    private boolean esIntercambiable(MaterialEntity m) {
        return m.getName() != null
                && m.getName().trim().toUpperCase().startsWith("TINTA");
    }

    private List<MaterialFaltanteDto> calcularFaltantes(OrdenProduccionEntity orden,
                                                        List<RecetaBolsaEntity> receta,
                                                        Map<Integer, MaterialEntity> materiales) {
        List<MaterialFaltanteDto> faltantes = new ArrayList<>();
        for (RecetaBolsaEntity r : receta) {
            MaterialEntity m = (materiales != null)
                    ? materiales.get(r.getMaterial().getIdMaterial())
                    : r.getMaterial();
            BigDecimal requerido = r.getCantidadRequerida().multiply(orden.getCantidadProducir());
            BigDecimal comprometido = ordenProduccion.materialComprometido(
                    m.getIdMaterial(), orden.getIdOrden());
            if (comprometido == null) comprometido = BigDecimal.ZERO;
            BigDecimal disponible = m.getStockActual().subtract(comprometido);

            if (disponible.compareTo(requerido) < 0) {
                faltantes.add(new MaterialFaltanteDto(
                        m.getIdMaterial(), m.getName(), requerido, disponible,
                        requerido.subtract(disponible)));
            }
        }
        return faltantes;
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
