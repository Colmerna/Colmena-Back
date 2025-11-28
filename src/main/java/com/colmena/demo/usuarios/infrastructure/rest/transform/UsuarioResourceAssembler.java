package com.colmena.demo.usuarios.infrastructure.rest.transform;

import com.colmena.demo.usuarios.domain.model.aggregates.Usuario;
import com.colmena.demo.usuarios.infrastructure.rest.resources.UsuarioResource;

public class UsuarioResourceAssembler {

    public static UsuarioResource toResource(Usuario usuario) {
        return new UsuarioResource(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail()
        );
    }
}
