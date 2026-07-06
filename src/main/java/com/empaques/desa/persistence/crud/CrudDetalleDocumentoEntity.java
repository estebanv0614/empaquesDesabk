package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.DetalleDocumentoEntity;
import org.springframework.data.repository.CrudRepository;

public interface CrudDetalleDocumentoEntity extends CrudRepository<DetalleDocumentoEntity, Integer> {
}
