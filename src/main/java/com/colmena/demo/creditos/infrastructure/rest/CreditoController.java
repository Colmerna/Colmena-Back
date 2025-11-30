package com.colmena.demo.creditos.infrastructure.rest;

import com.colmena.demo.creditos.application.commandservices.CreditoCommandService;
import com.colmena.demo.creditos.application.queryservices.CreditoQueryService;
import com.colmena.demo.creditos.domain.model.aggregates.Credito;
import com.colmena.demo.creditos.domain.model.entities.Cuota;
import com.colmena.demo.creditos.domain.model.entities.Resultado;
import com.colmena.demo.creditos.infrastructure.rest.resources.CreateCreditoResource;
import com.colmena.demo.creditos.infrastructure.rest.resources.CreditoResource;
import com.colmena.demo.creditos.infrastructure.rest.resources.CuotaResource;
import com.colmena.demo.creditos.infrastructure.rest.resources.ResultadoResource;
import com.colmena.demo.creditos.infrastructure.rest.transform.CreateCreditoCommandAssembler;
import com.colmena.demo.creditos.infrastructure.rest.transform.CreditoResourceAssembler;
import com.colmena.demo.creditos.infrastructure.rest.transform.CuotaResourceAssembler;
import com.colmena.demo.creditos.infrastructure.rest.transform.ResultadoResourceAssembler;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creditos")
public class CreditoController {

    private final CreditoCommandService commandService;
    private final CreditoQueryService queryService;

    public CreditoController(CreditoCommandService commandService,
                             CreditoQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    // ---------------------------------------------------------
    // 1) Crear crédito (solo registra, no simula aún)
    // ---------------------------------------------------------
    @PostMapping
    public CreditoResource crearCredito(@RequestBody CreateCreditoResource resource) {

        Credito credito = CreateCreditoCommandAssembler.toEntity(resource);
        Credito saved = commandService.crearCredito(credito);

        return CreditoResourceAssembler.toResource(saved);
    }

    // ---------------------------------------------------------
    // 2) Simular crédito (genera cuotas + resultado)
    // ---------------------------------------------------------
    @PostMapping("/{id}/simular")
    public ResultadoResource simularCredito(@PathVariable Long id) {

        Resultado resultado = commandService.simularCredito(id);
        return ResultadoResourceAssembler.toResource(resultado);
    }

    // ---------------------------------------------------------
    // 3) Obtener crédito por id
    // ---------------------------------------------------------
    @GetMapping("/{id}")
    public CreditoResource obtenerPorId(@PathVariable Long id) {

        Credito credito = queryService.obtenerPorId(id);
        return CreditoResourceAssembler.toResource(credito);
    }

    // ---------------------------------------------------------
    // 4) Obtener cuotas de un crédito
    // ---------------------------------------------------------
    @GetMapping("/{id}/cuotas")
    public List<CuotaResource> obtenerCuotas(@PathVariable Long id) {

        List<Cuota> cuotas = queryService.obtenerCuotas(id);

        return cuotas.stream()
                .map(CuotaResourceAssembler::toResource)
                .toList();
    }

    // ---------------------------------------------------------
    // 5) Obtener resultado de un crédito (TCEA, VAN, TIR, etc.)
    // ---------------------------------------------------------
    @GetMapping("/{id}/resultado")
    public ResultadoResource obtenerResultado(@PathVariable Long id) {

        Resultado resultado = queryService.obtenerResultado(id);
        return ResultadoResourceAssembler.toResource(resultado);
    }

    // ---------------------------------------------------------
    // 6) Obtener todos los créditos de un cliente
    // ---------------------------------------------------------
    @GetMapping("/cliente/{clienteId}")
    public List<CreditoResource> obtenerPorCliente(@PathVariable Long clienteId) {

        List<Credito> creditos = queryService.obtenerPorCliente(clienteId);

        return creditos.stream()
                .map(CreditoResourceAssembler::toResource)
                .toList();
    }

    // ---------------------------------------------------------
    // 7) Obtener todos los creditos (para dashboard)
    // ---------------------------------------------------------
    @GetMapping
    public List<CreditoResource> obtenerTodos() {

        List<Credito> creditos = queryService.obtenerTodos();

        return creditos.stream()
                .map(CreditoResourceAssembler::toResource)
                .toList();
    }
}
