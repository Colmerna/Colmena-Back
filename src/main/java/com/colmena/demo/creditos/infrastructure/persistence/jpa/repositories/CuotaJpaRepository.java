package com.colmena.demo.creditos.infrastructure.persistence.jpa.repositories;

import com.colmena.demo.creditos.domain.model.entities.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuotaJpaRepository extends JpaRepository<Cuota, Long> {
    List<Cuota> findByCreditoId(Long creditoId);
}
