package com.empaques.desa.domain.exception;

public class UserBlockedException extends RuntimeException {
    public UserBlockedException() {
        super("Usuario bloqueado");
    }
}
