package com.empaques.desa.domain.exception;

public class PersonAlreadyExistsException extends RuntimeException{
    public PersonAlreadyExistsException(String documentNumber){
        super("El documeto " + documentNumber + " ya existe");
    }


}
