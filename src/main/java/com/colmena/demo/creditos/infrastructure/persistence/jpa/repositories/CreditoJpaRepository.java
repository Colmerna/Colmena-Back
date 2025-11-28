package com.colmena.demo.creditos.infrastructure.persistence.jpa.repositories;

import com.colmena.demo.creditos.domain.model.aggregates.Credito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditoJpaRepository extends JpaRepository<Credito, Long> {
    List<Credito> findByClienteId(Long clienteId);
}
