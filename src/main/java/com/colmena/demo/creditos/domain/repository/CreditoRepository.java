package com.colmena.demo.creditos.domain.repository;

import com.colmena.demo.creditos.domain.model.aggregates.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {
    List<Credito> findByClienteId(Long clienteId);
}
