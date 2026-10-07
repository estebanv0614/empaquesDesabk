package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.ActualizarNominaRequest;
import com.empaques.desa.domain.dto.PagarNominaRequest;
import com.empaques.desa.domain.dto.PagoNominaDto;
import com.empaques.desa.domain.repository.PagoNominaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NominaService {
    private final PagoNominaRepository pagoNomina;

    public NominaService(PagoNominaRepository pagoNomina) {
        this.pagoNomina = pagoNomina;
    }

    public List<PagoNominaDto> listarSemana(LocalDate fecha) {
        return pagoNomina.listarSemana(fecha);
    }

    public List<PagoNominaDto> generarSemana(LocalDate fecha) {
        return pagoNomina.generarSemana(fecha);
    }

    public Optional<PagoNominaDto> actualizar(Integer id, ActualizarNominaRequest req) {
        return pagoNomina.actualizar(id, req);
    }

    public Optional<PagoNominaDto> pagar(Integer id, PagarNominaRequest req, String evidenciaUrl) {
        return pagoNomina.pagar(id, req, evidenciaUrl);
    }
}
