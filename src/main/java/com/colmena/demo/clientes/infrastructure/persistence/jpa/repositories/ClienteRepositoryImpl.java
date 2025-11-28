package com.colmena.demo.clientes.infrastructure.persistence.jpa.repositories;

import com.colmena.demo.clientes.domain.model.aggregates.Cliente;
import com.colmena.demo.clientes.domain.repository.ClienteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteJpaRepository jpaRepository;

    public ClienteRepositoryImpl(ClienteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        return jpaRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Cliente> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public boolean existsByDni(String dni) {
        return jpaRepository.existsByDni(dni);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
