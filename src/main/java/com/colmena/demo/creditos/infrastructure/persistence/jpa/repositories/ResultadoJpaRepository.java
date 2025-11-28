package com.colmena.demo.creditos.infrastructure.persistence.jpa.repositories;

import com.colmena.demo.creditos.domain.model.entities.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultadoJpaRepository extends JpaRepository<Resultado, Long> {
    Optional<Resultado> findByCreditoId(Long creditoId);
}
