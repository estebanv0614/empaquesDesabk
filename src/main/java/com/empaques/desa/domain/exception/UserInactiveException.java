package com.empaques.desa.domain.exception;

public class UserInactiveException extends RuntimeException {
    public UserInactiveException() {
        super("Usuario inactivo");
    }
}
