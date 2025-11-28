package com.colmena.demo.usuarios.infrastructure.rest.resources;

public record UsuarioResource(
        Long id,
        String username,
        String email
) {}
