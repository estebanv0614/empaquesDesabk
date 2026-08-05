package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.PedidoDto;
import com.empaques.desa.persistence.entity.PedidoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClientMapper.class, UserMapper.class, EstadoMapper.class, MetodoPagoMapper.class, DetallePedidoMapper.class})
public interface PedidoMapper {
    @Mapping(source = "idPedido", target = "id")
    @Mapping(source = "client", target = "client")
    @Mapping(source = "userVendedor", target = "userVendedor")
    @Mapping(source = "estado", target = "estado")
    @Mapping(source = "metodoPago", target = "metodoPago")
    @Mapping(source = "impuesto", target = "impuestos")
    @Mapping(source = "observacion", target = "observacion")
    @Mapping(source = "detallePedidos", target = "detalles")
    PedidoDto toDto(PedidoEntity pedido);

    List<PedidoDto> toDtoList(Iterable<PedidoEntity> entities);
}
