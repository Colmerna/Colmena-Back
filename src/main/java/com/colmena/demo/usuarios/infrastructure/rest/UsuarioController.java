package com.colmena.demo.usuarios.infrastructure.rest;

import com.colmena.demo.usuarios.application.commandservices.UsuarioCommandService;
import com.colmena.demo.usuarios.application.queryservices.UsuarioQueryService;
import com.colmena.demo.usuarios.domain.model.commands.RegisterUsuarioCommand;
import com.colmena.demo.usuarios.domain.model.queries.GetUsuarioByIdQuery;
import com.colmena.demo.usuarios.domain.model.queries.LoginUsuarioQuery;
import com.colmena.demo.usuarios.infrastructure.rest.resources.CreateUsuarioResource;
import com.colmena.demo.usuarios.infrastructure.rest.resources.LoginResource;
import com.colmena.demo.usuarios.infrastructure.rest.resources.UsuarioResource;
import com.colmena.demo.usuarios.infrastructure.rest.transform.UsuarioResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    private final UsuarioCommandService commandService;
    private final UsuarioQueryService queryService;

    public UsuarioController(UsuarioCommandService commandService,
                             UsuarioQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    // POST /usuarios
    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioResource> createUsuario(@RequestBody CreateUsuarioResource resource) {

        var command = new RegisterUsuarioCommand(
                resource.username(),
                resource.email(),
                resource.password()
        );

        Long id = commandService.handle(command);

        var usuarioOpt = queryService.handle(new GetUsuarioByIdQuery(id));

        return usuarioOpt
                .map(usuario -> {
                    UsuarioResource body = UsuarioResourceAssembler.toResource(usuario);
                    return ResponseEntity.created(URI.create("/api/usuarios/" + id)).body(body);
                })
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    // GET /usuarios/{id} -> obtener usuario por id
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResource> getUsuarioById(@PathVariable Long id) {

        var usuarioOpt = queryService.handle(new GetUsuarioByIdQuery(id));

        return usuarioOpt
                .map(usuario -> ResponseEntity.ok(UsuarioResourceAssembler.toResource(usuario)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /auth/login -> login simple con username + password
    @PostMapping("/auth/login")
    public ResponseEntity<UsuarioResource> login(@RequestBody LoginResource resource) {

        var query = new LoginUsuarioQuery(resource.username(), resource.password());

        var usuarioOpt = queryService.handle(query);

        return usuarioOpt
                .map(usuario -> ResponseEntity.ok(UsuarioResourceAssembler.toResource(usuario)))
                .orElseGet(() -> ResponseEntity.status(401).build());
    }
}
