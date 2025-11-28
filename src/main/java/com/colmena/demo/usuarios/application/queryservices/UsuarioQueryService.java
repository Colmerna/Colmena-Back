package com.colmena.demo.usuarios.application.queryservices;

import com.colmena.demo.usuarios.domain.model.aggregates.Usuario;
import com.colmena.demo.usuarios.domain.model.queries.GetUsuarioByIdQuery;
import com.colmena.demo.usuarios.domain.model.queries.LoginUsuarioQuery;
import com.colmena.demo.usuarios.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioQueryService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioQueryService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> handle(GetUsuarioByIdQuery query) {
        return usuarioRepository.findById(query.usuarioId());
    }

    public Optional<Usuario> handle(LoginUsuarioQuery query) {
        return usuarioRepository.findByUsernameAndPassword(
                query.username(),
                query.password()
        );
    }
}
