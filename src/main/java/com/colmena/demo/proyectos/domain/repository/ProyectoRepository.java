package com.colmena.demo.proyectos.domain.repository;

import com.colmena.demo.proyectos.domain.model.aggregates.Proyecto;

import java.util.List;
import java.util.Optional;

public interface ProyectoRepository {

    Proyecto save(Proyecto proyecto);

    Optional<Proyecto> findById(Long id);

    List<Proyecto> findAll();
}
