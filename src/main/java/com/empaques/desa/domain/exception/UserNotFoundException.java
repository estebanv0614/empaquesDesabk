package com.empaques.desa.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("El usuario " + username + " no existe");
    }
}
