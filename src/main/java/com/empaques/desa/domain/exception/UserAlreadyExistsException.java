package com.empaques.desa.domain.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(Integer idPerson) {
        super("La persona con id " + idPerson + " ya tiene un usuario asociado");
    }
}
