package com.empaques.desa.persistence.mapper;

import com.empaques.desa.domain.dto.OrdenProduccionDto;
import com.empaques.desa.persistence.entity.OrdenProduccionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EstadoMapper.class, DetallePedidoMapper.class, UserMapper.class})
public interface OrdenProduccionMapper {
    @Mapping(source = "idOrden", target = "id")
    @Mapping(source = "detallePedido", target = "detallePedido")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "estado", target = "estado")
    @Mapping(source = "detallePedido.pedido.numeroPedido", target = "numeroPedido")
    OrdenProduccionDto toDto(OrdenProduccionEntity entity);

    List<OrdenProduccionDto> toDtoList(Iterable<OrdenProduccionEntity> entities);

    @Mapping(source = "id", target = "idOrden")
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "detallePedido", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    OrdenProduccionEntity toEntity(OrdenProduccionDto dto);
}
