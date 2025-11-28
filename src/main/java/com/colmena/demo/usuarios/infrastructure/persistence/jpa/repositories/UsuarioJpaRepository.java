package com.colmena.demo.usuarios.infrastructure.persistence.jpa.repositories;

import com.colmena.demo.usuarios.domain.model.aggregates.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioJpaRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByUsernameAndPassword(String username, String password);

    boolean existsByUsernameOrEmail(String username, String email);
}
