package com.colmena.demo.usuarios.domain.model.commands;

public record RegisterUsuarioCommand(
        String username,
        String email,
        String password
) {}
