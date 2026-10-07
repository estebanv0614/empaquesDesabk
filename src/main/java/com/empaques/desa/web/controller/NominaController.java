package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.ActualizarNominaRequest;
import com.empaques.desa.domain.dto.PagarNominaForm;
import com.empaques.desa.domain.dto.PagarNominaRequest;
import com.empaques.desa.domain.dto.PagoNominaDto;
import com.empaques.desa.domain.service.ImagenService;
import com.empaques.desa.domain.service.NominaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/nomina")
public class NominaController {
    private final NominaService nomina;
    private final ImagenService imagen;

    public NominaController(NominaService nomina, ImagenService imagen) {
        this.nomina = nomina;
        this.imagen = imagen;
    }

    @GetMapping
    public List<PagoNominaDto> listar(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fecha) {
        return nomina.listarSemana(fecha);
    }

    @PostMapping("/generar")
    public  List<PagoNominaDto> generar(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return nomina.generarSemana(fecha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoNominaDto> actualizar(@PathVariable Integer id, @RequestBody ActualizarNominaRequest request) {
        return ResponseEntity.of(nomina.actualizar(id, request));
    }

    @PatchMapping(value = "/{id}/pagar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PagoNominaDto> pagar(@PathVariable Integer id, @ModelAttribute PagarNominaForm form) throws IOException {
        String evidenciaUrl = imagen.guardar(form.evidencia(), "gastos");
        return ResponseEntity.of(nomina.pagar(id, form.toRequest(), evidenciaUrl));
    }
}
