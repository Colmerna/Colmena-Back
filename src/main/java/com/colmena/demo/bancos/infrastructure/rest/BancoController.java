package com.colmena.demo.bancos.infrastructure.rest;

import com.colmena.demo.bancos.application.commandservices.BancoCommandService;
import com.colmena.demo.bancos.application.queryservices.BancoQueryService;
import com.colmena.demo.bancos.domain.model.commands.CreateBancosCommand;
import com.colmena.demo.bancos.domain.model.queries.GetAllBancosQuery;
import com.colmena.demo.bancos.domain.model.queries.GetBancoByIdQuery;
import com.colmena.demo.bancos.domain.model.commands.UpdateTipoCambioBancoCommand;
import com.colmena.demo.bancos.infrastructure.rest.resources.BancoResource;
import com.colmena.demo.bancos.infrastructure.rest.resources.CreateBancoResource;
import com.colmena.demo.bancos.infrastructure.rest.resources.TipoCambioResource;
import com.colmena.demo.bancos.infrastructure.rest.resources.UpdateTipoCambioResource;
import com.colmena.demo.bancos.infrastructure.rest.transform.BancoResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bancos")
public class BancoController {

    private final BancoQueryService queryService;
    private final BancoCommandService commandService;

    public BancoController(BancoQueryService queryService,
                           BancoCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    // GET /api/bancos
    @GetMapping
    public ResponseEntity<List<BancoResource>> getAllBancos() {

        var bancos = queryService.handle(new GetAllBancosQuery())
                .stream()
                .map(BancoResourceAssembler::toResource)
                .toList();

        return ResponseEntity.ok(bancos);
    }

    // GET /api/bancos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<BancoResource> getBancoById(@PathVariable Long id) {

        return queryService.handle(new GetBancoByIdQuery(id))
                .map(banco -> ResponseEntity.ok(BancoResourceAssembler.toResource(banco)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    // PUT /api/bancos/{id}/tipo-cambio
    @PutMapping("/{id}/tipo-cambio")
    public ResponseEntity<Void> updateTipoCambio(@PathVariable Long id,
                                                 @RequestBody UpdateTipoCambioResource resource) {

        var command = new UpdateTipoCambioBancoCommand(
                id,
                resource.tipoCambioCompra(),
                resource.tipoCambioVenta()
        );

        commandService.handle(command);
        return ResponseEntity.noContent().build();
    }

    // GET /api/bancos/{id}/tipo-cambio
    @GetMapping("/{id}/tipo-cambio")
    public ResponseEntity<TipoCambioResource> getTipoCambio(@PathVariable Long id) {

        var bancoOpt = queryService.handle(new GetBancoByIdQuery(id));

        if (bancoOpt.isEmpty())
            return ResponseEntity.notFound().build();

        var banco = bancoOpt.get();

        var resource = new TipoCambioResource(
                banco.getNombre(),
                banco.getMonedaBase(),
                banco.getTipoCambioCompra(),
                banco.getTipoCambioVenta()
        );

        return ResponseEntity.ok(resource);
    }



    @PostMapping
    public ResponseEntity<BancoResource> createBanco(@RequestBody CreateBancoResource resource) {

        var command = new CreateBancosCommand(
                resource.nombre(),
                resource.ruc(),
                resource.telefono(),
                resource.sitioWeb(),
                resource.monedaBase(),
                resource.usaTasasNominales(),
                resource.capitalizacionDefault(),
                resource.tipoCambioCompra(),
                resource.tipoCambioVenta(),
                resource.estado()
        );

        Long bancoId = commandService.handle(command);

        var bancoOpt = queryService.handle(new GetBancoByIdQuery(bancoId));

        var banco = bancoOpt.orElseThrow();

        return ResponseEntity.ok(BancoResourceAssembler.toResource(banco));
    }




}
