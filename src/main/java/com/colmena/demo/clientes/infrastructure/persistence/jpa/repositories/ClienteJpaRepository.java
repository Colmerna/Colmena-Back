package com.colmena.demo.clientes.infrastructure.persistence.jpa.repositories;

import com.colmena.demo.clientes.domain.model.aggregates.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteJpaRepository extends JpaRepository<Cliente, Long> {

    boolean existsByDni(String dni);

    boolean existsByEmail(String email);
}
