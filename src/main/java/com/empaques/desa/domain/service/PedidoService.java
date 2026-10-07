package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.DetallePedidoDto;
import com.empaques.desa.domain.dto.EstadisticaPeriodoDto;
import com.empaques.desa.domain.dto.PedidoDto;
import com.empaques.desa.domain.dto.ResumenPedidosDto;
import com.empaques.desa.domain.repository.OrdenProduccionRepository;
import com.empaques.desa.domain.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final OrdenProduccionRepository ordenProduccion;

    public PedidoService(PedidoRepository pedidoRepository, OrdenProduccionRepository ordenProduccion) {
        this.pedidoRepository = pedidoRepository;
        this.ordenProduccion = ordenProduccion;
    }

    public List<PedidoDto> getAll() {
        return pedidoRepository.getAll();
    }

    public Optional<PedidoDto> getById(Integer id) {
        return pedidoRepository.getById(id);
    }

    @Transactional
    public PedidoDto save(PedidoDto dto) {
        PedidoDto guardado = pedidoRepository.save(dto);
        for (DetallePedidoDto detale : guardado.detalles()) {
            ordenProduccion.crearDesdeDetalle(detale.id(), guardado.userVendedor().id());
        }
        return guardado;
    }

    public Optional<PedidoDto> update(Integer id, PedidoDto dto) {
        return pedidoRepository.update(id, dto);
    }

    public Optional<PedidoDto> updateEstado(Integer id, Integer idEstado) {
        return pedidoRepository.updateEstado(id, idEstado);
    }

    public Optional<PedidoDto> marcarComoPagado(Integer id) {
        return pedidoRepository.marcarComoPagado(id);
    }

    public boolean delete(Integer id) {
        return pedidoRepository.delete(id);
    }

    public List<PedidoDto> getByClientId(Integer idClient) {
        return pedidoRepository.getByClientId(idClient);
    }


    public ResumenPedidosDto getResumen() {
        return pedidoRepository.getResumen();
    }

    public List<EstadisticaPeriodoDto> getEstadisticasPorMes() {
        return pedidoRepository.getEstadisticasPorMes();
    }

    public List<EstadisticaPeriodoDto> getEstadisticasPorDia() {
        return pedidoRepository.getEstadisticasPorDia();
    }

    public List<PedidoDto> getByRangoFechas(LocalDateTime desde, LocalDateTime hasta) {
        return pedidoRepository.getByRangoFechas(desde, hasta);
    }
}
