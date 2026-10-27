package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.EstadisticaPeriodoDto;
import com.empaques.desa.domain.dto.PedidoDto;
import com.empaques.desa.domain.dto.ResumenPedidosDto;
import com.empaques.desa.domain.repository.PedidoRepository;
import com.empaques.desa.persistence.crud.*;
import com.empaques.desa.persistence.entity.BolsaEntity;
import com.empaques.desa.persistence.entity.DetallePedidoEntity;
import com.empaques.desa.persistence.entity.PedidoEntity;
import com.empaques.desa.persistence.mapper.PedidoMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class PedidoEntityRepository implements PedidoRepository {
    private static final Integer ID_ESTADO_PENDIENTE = 2;
    private static final Integer ID_ESTADO_ENTREGADO = 10;
    private static final Integer ID_ESTADO_CANCELADO = 11;
    private static final Integer ID_ESTADO_FINALIZADA = 13;

    private final CrudPedidoEntity crudPedido;
    private final CrudClientEntity crudClient;
    private final CrudUserEntity crudUser;
    private final CrudEstadoEntity crudEstado;
    private final CrudMetodoPagoEntity crudMetodoPago;
    private final CrudBolsaEntity crudBolsa;
    private final PedidoMapper pedidoMapper;

    public PedidoEntityRepository(CrudPedidoEntity crudPedido, CrudClientEntity crudClient, CrudUserEntity crudUser, CrudEstadoEntity crudEstado, CrudMetodoPagoEntity crudMetodoPago, CrudBolsaEntity crudBolsa, PedidoMapper pedidoMapper) {
        this.crudPedido = crudPedido;
        this.crudClient = crudClient;
        this.crudUser = crudUser;
        this.crudEstado = crudEstado;
        this.crudMetodoPago = crudMetodoPago;
        this.crudBolsa = crudBolsa;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    public List<PedidoDto> getAll() {
        return pedidoMapper.toDtoList(crudPedido.findAll());
    }

    @Override
    public Optional<PedidoDto> getById(Integer id) {
        return crudPedido.findById(id)
                .map(pedidoMapper::toDto);
    }

    @Override
    public PedidoDto save(PedidoDto dto) {
        PedidoEntity entity = new PedidoEntity();
        entity.setNumeroPedido(dto.numeroPedido());
        entity.setFechaEntregaEstimada(dto.fechaEntregaEstimada());
        entity.setSubtotal(dto.subtotal());
        entity.setImpuesto(dto.impuestos());
        entity.setTotal(dto.total());
        entity.setObservacion(dto.observacion());

        entity.setClient(
                crudClient.findById(dto.client().id())
                        .orElseThrow(() -> new RuntimeException("Cliente no encontrado " + dto.client().id()))
        );

        entity.setUserVendedor(
                crudUser.findById(dto.userVendedor().id())
                        .orElseThrow(() -> new RuntimeException("Usuario vendedor no encontrado " + dto.userVendedor().id()))
        );

        entity.setEstado(
                crudEstado.findById(ID_ESTADO_PENDIENTE)
                        .orElseThrow(() -> new RuntimeException("Estado PENDIENTE no encontrado"))
        );

        if (dto.metodoPago() != null) {
            entity.setMetodoPago(
                    crudMetodoPago.findById(dto.metodoPago().id())
                            .orElseThrow(() -> new RuntimeException("Método de pago no encontrado " + dto.metodoPago().id()))
            );
        }
        List<DetallePedidoEntity> detalles = new java.util.ArrayList<>();
        for (int i = 0; i < dto.detalles().size(); i++) {
            var detalleDto = dto.detalles().get(i);
            DetallePedidoEntity detalle = new DetallePedidoEntity();
            detalle.setPedido(entity);
            detalle.setCantidad(detalleDto.cantidad());
            detalle.setPrecioUnitarioVenta(detalleDto.precioUnitarioVenta());
            detalle.setSubtotalLinea(detalleDto.subtotalLinea());

            BolsaEntity bolsaReal = crudBolsa.findById(detalleDto.bolsa().id())
                    .orElseThrow(() -> new RuntimeException("Bolsa no encontrada " + detalleDto.bolsa().id()));
            detalle.setBolsa(bolsaReal);

            detalles.add(detalle);
        }
        entity.setDetallePedidos(detalles);

        return pedidoMapper.toDto(crudPedido.save(entity));
    }

    @Override
    public Optional<PedidoDto> update(Integer id, PedidoDto dto) {
        return crudPedido.findById(id)
                .map(entity -> {
                    entity.setFechaEntregaEstimada(dto.fechaEntregaEstimada());
                    entity.setSubtotal(dto.subtotal());
                    entity.setImpuesto(dto.impuestos());
                    entity.setTotal(dto.total());
                    entity.setObservacion(dto.observacion());

                    if (dto.metodoPago() != null) {
                        entity.setMetodoPago(
                                crudMetodoPago.findById(dto.metodoPago().id())
                                        .orElseThrow(() -> new RuntimeException("Metodo de pago no encontrado " + dto.metodoPago().id()))
                        );
                    }
                    PedidoEntity updated = crudPedido.save(entity);
                    return pedidoMapper.toDto(updated);
                });
    }

    @Override
    public Optional<PedidoDto> updateEstado(Integer id, Integer idEstado) {
        return crudPedido.findById(id)
                .map(entity -> {
                    Integer actual = entity.getEstado().getIdState();

                    if (ID_ESTADO_ENTREGADO.equals(actual) || ID_ESTADO_CANCELADO.equals(actual)) {
                        throw new IllegalStateException(
                                "El pedido ya está en un estado final y no se puede cambiar");
                    }
                    if (!ID_ESTADO_ENTREGADO.equals(idEstado) && !ID_ESTADO_CANCELADO.equals(idEstado)) {
                        throw new IllegalStateException(
                                "El estado del pedido avanza automáticamente con la producción");
                    }
                    if (ID_ESTADO_ENTREGADO.equals(idEstado) && !ID_ESTADO_FINALIZADA.equals(actual)) {
                        throw new IllegalStateException(
                                "Solo se puede entregar un pedido con producción finalizada");
                    }
                    entity.setEstado(
                            crudEstado.findById(idEstado)
                                    .orElseThrow(() -> new RuntimeException("Estado no encontrado " + idEstado))
                    );
                    return pedidoMapper.toDto(crudPedido.save(entity));
                });
    }

    @Override
    public Optional<PedidoDto> marcarComoPagado(Integer id) {
        return crudPedido.findById(id)
                .map(entity -> {
                    entity.setPagado(true);
                    entity.setFechaPago(LocalDateTime.now());
                    PedidoEntity actualizado = crudPedido.save(entity);
                    return pedidoMapper.toDto(actualizado);
                });
    }

    @Override
    public boolean delete(Integer id) {
        return crudPedido.findById(id)
                .map(entity -> {
                    entity.setDeletedAt(LocalDateTime.now());
                    crudPedido.save(entity);
                    return true;
                }).orElse(false);
    }

    @Override
    public List<PedidoDto> getByClientId(Integer idClient) {
        return pedidoMapper.toDtoList(crudPedido.findByClient_IdClient(idClient));
    }

    @Override
    public Optional<PedidoDto> marcarComoEntregado(Integer id) {
        return crudPedido.findById(id)
                .map(entity -> {
                    if (Boolean.TRUE.equals(entity.getEntregado())) {
                        throw new IllegalStateException("El pedido ya fue entregado");
                    }
                    if (!ID_ESTADO_FINALIZADA.equals(entity.getEstado().getIdState())) {
                        throw new IllegalStateException("Solo se puede entregar un pedido con producción finalizada");
                    }
                    entity.setEntregado(true);
                    entity.setFechaEntrega(LocalDateTime.now());
                    return pedidoMapper.toDto(crudPedido.save(entity));
                });
    }

    @Override
    public ResumenPedidosDto getResumen() {
        return new ResumenPedidosDto(
                crudPedido.countHoy(),
                crudPedido.countEstaSemana(),
                crudPedido.countEsteMes(),
                crudPedido.countEsteAnio()
        );
    }

    @Override
    public List<EstadisticaPeriodoDto> getEstadisticasPorMes() {
        return crudPedido.countPorMes().stream()
                .map(row -> new EstadisticaPeriodoDto(row[0].toString(), ((Number) row[1]).longValue()))
                .toList();
    }

    @Override
    public List<EstadisticaPeriodoDto> getEstadisticasPorDia() {
        return crudPedido.countPorDia().stream()
                .map(row -> new EstadisticaPeriodoDto(row[0].toString(), ((Number) row[1]).longValue()))
                .toList();
    }

    @Override
    public List<PedidoDto> getByRangoFechas(LocalDateTime desde, LocalDateTime hasta) {
        return pedidoMapper.toDtoList(crudPedido.findForExport(desde, hasta));
    }
}
