package com.colmena.demo.clientes.domain.repository;

import com.colmena.demo.clientes.domain.model.aggregates.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {

    Cliente save(Cliente cliente);

    Optional<Cliente> findById(Long id);

    List<Cliente> findAll();

    boolean existsByDni(String dni);

    boolean existsByEmail(String email);
}
