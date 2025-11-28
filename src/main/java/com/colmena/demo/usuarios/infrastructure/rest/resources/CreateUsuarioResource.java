package com.colmena.demo.usuarios.infrastructure.rest.resources;

public record CreateUsuarioResource(
        String username,
        String email,
        String password
) {}
