package com.empaques.desa.domain.exception;

public class SolicitudYaConvertidaException extends RuntimeException {
    private final Integer documetoComercialId;

    public SolicitudYaConvertidaException(Integer documetoComercialId) {
        super("La solicitud ya fue convertida a documento comercial");
        this.documetoComercialId = documetoComercialId;
    }

    public Integer getDocumetoComercialId() {
        return documetoComercialId;
    }
}
