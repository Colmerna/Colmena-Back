package com.colmena.demo.proyectos.infrastructure.persistence.jpa.repositories;

import com.colmena.demo.proyectos.domain.model.aggregates.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProyectoJpaRepository extends JpaRepository<Proyecto, Long> {
}
