package com.empaques.desa.persistence;

import com.empaques.desa.domain.TipoMovimiento;
import com.empaques.desa.domain.dto.GastoOperativoDto;
import com.empaques.desa.domain.dto.GastoOperativoRequest;
import com.empaques.desa.domain.repository.GastoOperativoRepository;
import com.empaques.desa.persistence.crud.*;
import com.empaques.desa.persistence.entity.GastoOperativoEntity;
import com.empaques.desa.persistence.entity.MaterialEntity;
import com.empaques.desa.persistence.entity.MovimientoInventarioEntity;
import com.empaques.desa.persistence.entity.UserEntity;
import com.empaques.desa.persistence.mapper.GastoOperativoMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class GastoOperativoEntityRepository implements GastoOperativoRepository {
    private final CrudGastoOperativoEntity gastoOperativo;
    private final CrudTipoGastoEntity tipoGasto;
    private final CrudMetodoPagoEntity metodoPago;
    private final CrudEmployeeEntity employee;
    private final CrudMaterialEntity materialEntity;
    private final CrudMovimientoInventarioEntity movimientoInventario;
    private final CrudUserEntity user;
    private final GastoOperativoMapper gastoOperativoMapper;

    public GastoOperativoEntityRepository(CrudGastoOperativoEntity gastoOperativo, CrudTipoGastoEntity tipoGasto, CrudMetodoPagoEntity metodoPago, CrudEmployeeEntity employee, CrudMaterialEntity material, CrudMaterialEntity materialEntity, CrudMovimientoInventarioEntity movimientoInventario, CrudUserEntity user, GastoOperativoMapper gastoOperativoMapper) {
        this.gastoOperativo = gastoOperativo;
        this.tipoGasto = tipoGasto;
        this.metodoPago = metodoPago;
        this.employee = employee;
        this.materialEntity = materialEntity;
        this.movimientoInventario = movimientoInventario;
        this.user = user;
        this.gastoOperativoMapper = gastoOperativoMapper;
    }

    @Override
    public List<GastoOperativoDto> getAll() {
        return gastoOperativoMapper.toDtoList(gastoOperativo.findAll());
    }

    @Override
    public Optional<GastoOperativoDto> getById(Integer id) {
        return gastoOperativo.findById(id).map(gastoOperativoMapper::toDto);
    }

    @Override
    @Transactional
    public GastoOperativoDto save(GastoOperativoRequest request, String evidenciaUrl) {
        GastoOperativoEntity gasto = new GastoOperativoEntity();
        gasto.setTipoGasto(tipoGasto.findById(request.idTipoGasto())
                .orElseThrow(() -> new RuntimeException("Tipo de gasto no encontrado")));
        gasto.setMonto(request.monto());
        gasto.setFechaGasto(request.fechaGasto());
        gasto.setDescripcion(request.descripcion());
        gasto.setMetodoPago(metodoPago.findById(request.idMetodoPago())
                .orElseThrow(() -> new  RuntimeException("Método de pago no encontrado")));
        gasto.setAdminResponsable(employee.findById(request.idEmpleado())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado")));
        gasto.setEvidenciaUrl(evidenciaUrl);

        MaterialEntity materiales = null;
        if (request.idMaterial() != null) {
            if (request.cantidad() == null || request.cantidad().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Debes indicar una cantidad mayor a 0 para la compra de material");
            }
            materiales = materialEntity.findByIdForUpdate(request.idMaterial())
                    .orElseThrow(() -> new RuntimeException("Material no encontrado"));
            gasto.setMaterial(materiales);
            gasto.setCantidad(request.cantidad());
        }

        GastoOperativoEntity guardado = gastoOperativo.save(gasto);
        if (materiales != null) {
            BigDecimal stockAntes = materiales.getStockActual() != null ? materiales.getStockActual() : BigDecimal.ZERO;
            BigDecimal stockDespues = stockAntes.add(request.cantidad());
            materiales.setStockActual(stockDespues);
            materialEntity.save(materiales);

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            UserEntity usuario = user.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));

            MovimientoInventarioEntity movi = new MovimientoInventarioEntity();
            movi.setMaterial(materiales);
            movi.setUser(usuario);
            movi.setTipoMovimiento(TipoMovimiento.ENTRADA);
            movi.setCantidad(request.cantidad());
            movi.setStockAntes(stockAntes);
            movi.setStockDespues(stockDespues);
            movi.setReferencia("Gasto operativo #" + guardado.getId());
            movi.setObservacion("Compra de material");
            movi.setIdGasto(guardado.getId());
            movi.setCostoUnitario(request.monto().divide(request.cantidad(), 2, RoundingMode.HALF_UP));
            movimientoInventario.save(movi);
        }
        return gastoOperativoMapper.toDto(guardado);
    }

    @Override
    public boolean delete(Integer id) {
        return gastoOperativo.findById(id).map(g -> {
            g.setDeletedAt(LocalDateTime.now());
            gastoOperativo.save(g);
            return true;
        }).orElse(false);
    }
}
