package com.colmena.demo.creditos.domain.repository;

import com.colmena.demo.creditos.domain.model.entities.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {
    Optional<Resultado> findByCreditoId(Long creditoId);

}
