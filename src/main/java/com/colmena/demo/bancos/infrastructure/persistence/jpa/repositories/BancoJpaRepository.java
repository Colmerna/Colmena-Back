package com.colmena.demo.bancos.infrastructure.persistence.jpa.repositories;

import com.colmena.demo.bancos.domain.model.aggregates.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancoJpaRepository extends JpaRepository<Banco, Long> {
}
