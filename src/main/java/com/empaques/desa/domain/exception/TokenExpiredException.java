package com.empaques.desa.domain.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException() {
        super("Token expirado");
    }
}
