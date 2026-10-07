package com.empaques.desa.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoOperativoForm(
        @NotNull Integer idTipoGasto,
        @NotNull @Positive BigDecimal monto,
        @NotNull LocalDate fechaGasto,
        @NotBlank String descripcion,
        @NotNull Integer idMetodoPago,
        @NotNull Integer idEmpleado,
        Integer idMaterial,
        BigDecimal cantidad,
        MultipartFile evidencia
) {
    public GastoOperativoRequest toRequest() {
        return new GastoOperativoRequest(idTipoGasto, monto, fechaGasto, descripcion,
                idMetodoPago, idEmpleado, idMaterial, cantidad);
    }
}
