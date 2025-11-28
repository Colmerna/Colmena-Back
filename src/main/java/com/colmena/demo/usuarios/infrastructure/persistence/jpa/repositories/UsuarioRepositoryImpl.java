package com.colmena.demo.usuarios.infrastructure.persistence.jpa.repositories;

import com.colmena.demo.usuarios.domain.model.aggregates.Usuario;
import com.colmena.demo.usuarios.domain.repository.UsuarioRepository;
import com.colmena.demo.usuarios.infrastructure.persistence.jpa.repositories.UsuarioJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioJpaRepository jpaRepository;

    public UsuarioRepositoryImpl(UsuarioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Usuario save(Usuario usuario) {
        return jpaRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return jpaRepository.findByUsername(username);
    }

    @Override
    public Optional<Usuario> findByUsernameAndPassword(String username, String password) {
        return jpaRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public boolean existsByUsernameOrEmail(String username, String email) {
        return jpaRepository.existsByUsernameOrEmail(username, email);
    }
}
