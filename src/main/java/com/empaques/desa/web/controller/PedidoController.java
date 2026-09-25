package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.*;
import com.empaques.desa.domain.service.ClientService;
import com.empaques.desa.domain.service.ExcelPedidoService;
import com.empaques.desa.domain.service.PedidoService;
import com.empaques.desa.domain.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;
    private final UserService userService;
    private final ClientService clientService;
    private final ExcelPedidoService excelPedido;

    public PedidoController(PedidoService pedidoService, UserService userService, ClientService clientService, ExcelPedidoService excelPedido) {
        this.pedidoService = pedidoService;
        this.userService = userService;
        this.clientService = clientService;
        this.excelPedido = excelPedido;
    }

    @GetMapping
    public List<PedidoDto> getAll() {
        return pedidoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> getById(@PathVariable Integer id) {
        return pedidoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mis-pedidos")
    public ResponseEntity<List<PedidoDto>> getMisPedidos(Authentication authentication) {
        String username = authentication.getName();

        UserDto user = userService.getByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ClientDto client = clientService.getByPersonId(user.person().id())
                .orElseThrow(() -> new RuntimeException("Este usuario no tiene un cliente asociado"));

        return ResponseEntity.ok(pedidoService.getByClientId(client.id()));
    }

    @PostMapping
    public ResponseEntity<PedidoDto> save(@RequestBody PedidoDto dto) {
        return ResponseEntity.ok(pedidoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDto> update(@PathVariable Integer id, @RequestBody PedidoDto dto) {
        return pedidoService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoDto> updateEstado(@PathVariable Integer id, @RequestBody Map<String, Integer> body) {
        Integer idEstado = body.get("idEstado");
        return pedidoService.updateEstado(id, idEstado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/pagar")
    public ResponseEntity<PedidoDto> marcarComoPagado(@PathVariable Integer id) {
        return pedidoService.marcarComoPagado(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = pedidoService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estadisticas/resumen")
    public ResponseEntity<ResumenPedidosDto> getResumen() {
        return ResponseEntity.ok(pedidoService.getResumen());
    }

    @GetMapping("/estadisticas/por-mes")
    public List<EstadisticaPeriodoDto> getEstadisticasPorMes() {
        return pedidoService.getEstadisticasPorMes();
    }

    @GetMapping("/estadisticas/por-dia")
    public  List<EstadisticaPeriodoDto> getEstadisticasPorDia() {
        return pedidoService.getEstadisticasPorDia();
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportar(
            @RequestParam String periodo,
            @RequestParam(required = false) String fecha
    ) throws IOException {

        LocalDate base = fecha != null ? LocalDate.parse(fecha) : LocalDate.now();
        LocalDateTime desde;
        LocalDateTime hasta;

        switch (periodo) {
            case "semana" -> {
                desde = base.with(DayOfWeek.MONDAY).atStartOfDay();
                hasta = base.with(DayOfWeek.SUNDAY).atTime(23, 59, 59);
            }
            case "mes" -> {
                desde = base.withDayOfMonth(1).atStartOfDay();
                hasta = base.withDayOfMonth(base.lengthOfMonth()).atTime(23, 59, 59);
            }
            case "anio" -> {
                desde = base.withDayOfYear(1).atStartOfDay();
                hasta = base.withDayOfYear(base.lengthOfYear()).atTime(23, 59, 59);
            }
            default -> throw new IllegalArgumentException("Período inválido: " + periodo);
        }

        List<PedidoDto> pedidos = pedidoService.getByRangoFechas(desde, hasta);
        byte[] excelBytes = excelPedido.generarExcel(pedidos);

        String nombreArchivo = "pedidos_" + periodo + "_" + base + ".xlsx";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivo)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excelBytes);
    }
}
