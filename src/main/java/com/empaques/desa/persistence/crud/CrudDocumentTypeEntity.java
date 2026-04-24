package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.DocumentTypeEntity;
import org.springframework.data.repository.CrudRepository;

public interface CrudDocumentTypeEntity extends CrudRepository<DocumentTypeEntity, Integer> {
}
