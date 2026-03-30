package com.empaques.desa.persistence.crud;

import com.empaques.desa.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface CrudUserEntity extends CrudRepository<UserEntity, Integer> {
}
