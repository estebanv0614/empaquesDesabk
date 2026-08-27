package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.EstadisticaPeriodoDto;
import com.empaques.desa.domain.dto.PedidoDto;
import com.empaques.desa.domain.dto.ResumenPedidosDto;
import com.empaques.desa.domain.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<PedidoDto> getAll() {
        return pedidoRepository.getAll();
    }

    public Optional<PedidoDto> getById(Integer id) {
        return pedidoRepository.getById(id);
    }

    public PedidoDto save(PedidoDto dto) {
        return pedidoRepository.save(dto);
    }

    public Optional<PedidoDto> update(Integer id, PedidoDto dto) {
        return pedidoRepository.update(id, dto);
    }

    public Optional<PedidoDto> updateEstado(Integer id, Integer idEstado) {
        return pedidoRepository.updateEstado(id, idEstado);
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
}
