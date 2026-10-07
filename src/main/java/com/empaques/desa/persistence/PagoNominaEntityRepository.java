package com.empaques.desa.persistence;

import com.empaques.desa.domain.dto.ActualizarNominaRequest;
import com.empaques.desa.domain.dto.PagarNominaRequest;
import com.empaques.desa.domain.dto.PagoNominaDto;
import com.empaques.desa.domain.repository.PagoNominaRepository;
import com.empaques.desa.persistence.crud.*;
import com.empaques.desa.persistence.entity.EmployeeEntity;
import com.empaques.desa.persistence.entity.GastoOperativoEntity;
import com.empaques.desa.persistence.entity.MetodoPagoEntity;
import com.empaques.desa.persistence.entity.PagoNominaEntity;
import com.empaques.desa.persistence.mapper.PagoNominaMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class PagoNominaEntityRepository implements PagoNominaRepository {
    private final CrudPagoNominaEntity pagoNomina;
    private final CrudEmployeeEntity employee;
    private final CrudGastoOperativoEntity gastoOperativo;
    private final CrudTipoGastoEntity tipoGasto;
    private final CrudMetodoPagoEntity metodoPago;
    private final PagoNominaMapper nominaMapper;

    public PagoNominaEntityRepository(CrudPagoNominaEntity pagoNomina, CrudEmployeeEntity employee, CrudGastoOperativoEntity gastoOperativo, CrudTipoGastoEntity tipoGasto, CrudMetodoPagoEntity metodoPago, PagoNominaMapper nominaMapper) {
        this.pagoNomina = pagoNomina;
        this.employee = employee;
        this.gastoOperativo = gastoOperativo;
        this.tipoGasto = tipoGasto;
        this.metodoPago = metodoPago;
        this.nominaMapper = nominaMapper;
    }

    @Override
    public List<PagoNominaDto> listarSemana(LocalDate fecha) {
        return nominaMapper.toDtoList(pagoNomina.findByFechaInicio(fecha.with(DayOfWeek.MONDAY)));
    }

    @Override
    @Transactional
    public List<PagoNominaDto> generarSemana(LocalDate fecha) {
        LocalDate inicio = fecha.with(DayOfWeek.MONDAY);
        LocalDate fin = inicio.plusDays(5);

        for (EmployeeEntity mp : employee.findByEstado_NameIgnoreCase("ACTIVO")) {
            if (pagoNomina.existsByEmployeeAndFechaInicio(mp, inicio)) continue;

            BigDecimal salario = mp.getSalary() != null ? mp.getSalary() : BigDecimal.ZERO;
            PagoNominaEntity pagar = new PagoNominaEntity();
            pagar.setEmployee(mp);
            pagar.setFechaInicio(inicio);
            pagar.setFechaFin(fin);
            pagar.setSalarioBase(salario);
            pagar.setTotalPagar(salario);
            pagoNomina.save(pagar);
        }
        return listarSemana(inicio);
    }

    @Override
    @Transactional
    public Optional<PagoNominaDto> actualizar(Integer id, ActualizarNominaRequest req) {
        return pagoNomina.findById(id).map(pago -> {
            if (Boolean.TRUE.equals(pago.getPagado())) {
                throw new IllegalStateException("No se puede modificar una nómina ya pagada");
            }
            BigDecimal bonos = req.bonificaciones() != null ? req.bonificaciones() : BigDecimal.ZERO;
            BigDecimal descu = req.deducciones() != null ? req.deducciones() : BigDecimal.ZERO;
            pago.setBonificaciones(bonos);
            pago.setDeducciones(descu);
            pago.setTotalPagar(pago.getSalarioBase().add(bonos).subtract(descu));
            pago.setObservacion(req.observacion());
            return nominaMapper.toDto(pagoNomina.save(pago));
        });
    }

    @Override
    @Transactional
    public Optional<PagoNominaDto> pagar(Integer id, PagarNominaRequest req, String evidenciaUrl) {
        return pagoNomina.findById(id).map(pago -> {
            if (Boolean.TRUE.equals(pago.getPagado())) return nominaMapper.toDto(pago);

            MetodoPagoEntity metodo = metodoPago.findById(req.idMetodoPago())
                    .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

            GastoOperativoEntity gasto = new GastoOperativoEntity();
            gasto.setTipoGasto(tipoGasto.findByNameIgnoreCase("NOMINA")
                    .orElseThrow(() -> new RuntimeException("Falta el tipo de gasto NOMINA")));
            gasto.setMonto(pago.getTotalPagar());
            gasto.setFechaGasto(LocalDate.now());
            gasto.setDescripcion("Nómina " + pago.getFechaInicio() + " a " + pago.getFechaFin()
                    + " - " + pago.getEmployee().getPerson().getName());
            gasto.setMetodoPago(metodo);
            gasto.setAdminResponsable(employee.findById(req.idEmpleado())
                    .orElseThrow(() -> new RuntimeException("Empleado responsable no encontrado")));
            gasto.setEvidenciaUrl(evidenciaUrl); // <-- nuevo
            gasto = gastoOperativo.save(gasto);

            pago.setPagado(true);
            pago.setFechaPago(LocalDate.now());
            pago.setMetodoPago(metodo);
            pago.setGasto(gasto);
            return nominaMapper.toDto(pagoNomina.save(pago));
        });
    }
}
