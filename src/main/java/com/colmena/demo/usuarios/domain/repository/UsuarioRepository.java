package com.colmena.demo.usuarios.domain.repository;

import com.colmena.demo.usuarios.domain.model.aggregates.Usuario;

import java.util.Optional;

public interface UsuarioRepository {

    Usuario save(Usuario usuario);

    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByUsernameAndPassword(String username, String password);

    boolean existsByUsernameOrEmail(String username, String email);
}
