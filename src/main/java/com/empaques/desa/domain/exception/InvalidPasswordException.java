package com.empaques.desa.domain.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Password is invalid");
    }
}
