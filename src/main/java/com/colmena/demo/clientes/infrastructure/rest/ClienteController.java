package com.colmena.demo.clientes.infrastructure.rest;


import com.colmena.demo.clientes.application.commandservices.ClienteCommandService;
import com.colmena.demo.clientes.application.queryservices.ClienteQueryService;
import com.colmena.demo.clientes.domain.model.commands.RegisterClienteCommand;
import com.colmena.demo.clientes.domain.model.commands.UpdateClienteCommand;
import com.colmena.demo.clientes.domain.model.queries.GetAllClientesQuery;
import com.colmena.demo.clientes.domain.model.queries.GetClienteByIdQuery;
import com.colmena.demo.clientes.infrastructure.rest.resources.ClienteResource;
import com.colmena.demo.clientes.infrastructure.rest.resources.CreateClienteResource;
import com.colmena.demo.clientes.infrastructure.rest.resources.UpdateClienteResource;
import com.colmena.demo.clientes.infrastructure.rest.transform.ClienteResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteCommandService commandService;
    private final ClienteQueryService queryService;

    public ClienteController(ClienteCommandService commandService,
                             ClienteQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    // POST /api/clientes
    @PostMapping
    public ResponseEntity<ClienteResource> createCliente(@RequestBody CreateClienteResource resource) {

        var command = new RegisterClienteCommand(
                resource.dni(),
                resource.nombres(),
                resource.apellidos(),
                resource.telefono(),
                resource.email(),
                resource.ingresoMensual(),
                resource.dependientes(),
                resource.scoreRiesgo(),
                resource.gastoMensualAprox(),
                resource.usuarioId(),
                resource.situacionLaboral(),
                resource.estadoCivil()
        );

        Long id = commandService.handle(command);

        return queryService.handle(new GetClienteByIdQuery(id))
                .map(cliente -> {
                    ClienteResource body = ClienteResourceAssembler.toResource(cliente);
                    return ResponseEntity.created(URI.create("/api/clientes/" + id)).body(body);
                })
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    // GET /api/clientes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResource> getClienteById(@PathVariable Long id) {

        return queryService.handle(new GetClienteByIdQuery(id))
                .map(cliente -> ResponseEntity.ok(ClienteResourceAssembler.toResource(cliente)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT /api/clientes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCliente(@PathVariable Long id,
                                              @RequestBody UpdateClienteResource resource) {

        var command = new UpdateClienteCommand(
                id,
                resource.telefono(),
                resource.email(),
                resource.ingresoMensual(),
                resource.dependientes(),
                resource.scoreRiesgo(),
                resource.gastoMensualAprox(),
                resource.situacionLaboral(),
                resource.estadoCivil()
        );

        commandService.handle(command);
        return ResponseEntity.noContent().build();
    }

    // GET /api/clientes
    @GetMapping
    public ResponseEntity<List<ClienteResource>> getAllClientes() {

        var clientes = queryService.handle(new GetAllClientesQuery())
                .stream()
                .map(ClienteResourceAssembler::toResource)
                .toList();

        return ResponseEntity.ok(clientes);
    }
}
