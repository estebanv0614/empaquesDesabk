package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.DetallePedidoDto;
import com.empaques.desa.persistence.entity.DetallePedidoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BolsaMapper.class})
public interface DetallePedidoMapper {
    @Mapping(source = "idDetallePedido", target = "id")
    @Mapping(source = "bolsa", target = "bolsa")
    DetallePedidoDto toDto(DetallePedidoEntity dto);

    List<DetallePedidoDto> toDtoList(Iterable<DetallePedidoEntity> entities);
}
