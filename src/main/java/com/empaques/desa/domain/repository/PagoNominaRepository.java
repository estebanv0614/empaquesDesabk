package com.empaques.desa.domain.repository;

import com.empaques.desa.domain.dto.ActualizarNominaRequest;
import com.empaques.desa.domain.dto.PagarNominaRequest;
import com.empaques.desa.domain.dto.PagoNominaDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PagoNominaRepository {
    List<PagoNominaDto> listarSemana(LocalDate fecha);
    List<PagoNominaDto> generarSemana(LocalDate fecha);
    Optional<PagoNominaDto> actualizar(Integer id, ActualizarNominaRequest req);
    Optional<PagoNominaDto> pagar(Integer id, PagarNominaRequest req, String evidenciaUrl);
}
