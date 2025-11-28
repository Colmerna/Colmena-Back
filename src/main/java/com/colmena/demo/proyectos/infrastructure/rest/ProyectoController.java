package com.colmena.demo.proyectos.infrastructure.rest;


import com.colmena.demo.proyectos.application.commandservices.ProyectoCommandService;
import com.colmena.demo.proyectos.application.queryservices.ProyectoQueryService;
import com.colmena.demo.proyectos.domain.model.commands.CreateProyectoCommand;
import com.colmena.demo.proyectos.domain.model.commands.UpdateProyectoCommand;
import com.colmena.demo.proyectos.domain.model.queries.GetAllProyectosQuery;
import com.colmena.demo.proyectos.domain.model.queries.GetProyectoByIdQuery;
import com.colmena.demo.proyectos.infrastructure.rest.resources.CreateProyectoResource;
import com.colmena.demo.proyectos.infrastructure.rest.resources.ProyectoResource;
import com.colmena.demo.proyectos.infrastructure.rest.resources.UpdateProyectoResource;
import com.colmena.demo.proyectos.infrastructure.rest.transform.ProyectoResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    private final ProyectoQueryService queryService;
    private final ProyectoCommandService commandService;

    public ProyectoController(ProyectoQueryService queryService,
                              ProyectoCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    // GET /api/proyectos?estado=DISPONIBLE&tipo=DEPARTAMENTO&distrito=Surco
    @GetMapping
    public ResponseEntity<List<ProyectoResource>> getAllProyectos() {

        var query = new GetAllProyectosQuery();

        var recursos = queryService.handle(query)
                .stream()
                .map(ProyectoResourceAssembler::toResource)
                .toList();

        return ResponseEntity.ok(recursos);
    }

    // GET /api/proyectos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProyectoResource> getProyectoById(@PathVariable Long id) {

        return queryService.handle(new GetProyectoByIdQuery(id))
                .map(p -> ResponseEntity.ok(ProyectoResourceAssembler.toResource(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT /api/proyectos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProyecto(@PathVariable Long id,
                                               @RequestBody UpdateProyectoResource resource) {

        var command = new UpdateProyectoCommand(
                id,
                resource.nombre(),
                resource.direccion(),
                resource.distrito(),
                resource.areaM2(),
                resource.precio(),
                resource.igv(),
                resource.numHabitaciones(),
                resource.tipoProyecto(),
                resource.estadoProyecto(),
                resource.bancoId()
        );

        commandService.handle(command);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ProyectoResource> createProyecto(
            @RequestBody CreateProyectoResource resource) {

        var command = new CreateProyectoCommand(
                resource.nombre(),
                resource.direccion(),
                resource.distrito(),
                resource.areaM2(),
                resource.precio(),
                resource.igv(),
                resource.numHabitaciones(),
                resource.tipoProyecto(),
                resource.estadoProyecto(),
                resource.bancoId()
        );

        Long id = commandService.handle(command);

        return queryService.handle(new GetProyectoByIdQuery(id))
                .map(p -> ResponseEntity.ok(ProyectoResourceAssembler.toResource(p)))
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }


}
