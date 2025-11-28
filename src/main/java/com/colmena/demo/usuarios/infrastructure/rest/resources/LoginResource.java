package com.colmena.demo.usuarios.infrastructure.rest.resources;

public record LoginResource(
        String username,
        String password
) {}
