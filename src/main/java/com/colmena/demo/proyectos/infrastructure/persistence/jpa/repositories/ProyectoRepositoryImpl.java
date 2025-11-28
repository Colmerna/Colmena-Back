package com.colmena.demo.proyectos.infrastructure.persistence.jpa.repositories;

import com.colmena.demo.proyectos.domain.model.aggregates.Proyecto;
import com.colmena.demo.proyectos.domain.repository.ProyectoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProyectoRepositoryImpl implements ProyectoRepository {

    private final ProyectoJpaRepository jpaRepository;

    public ProyectoRepositoryImpl(ProyectoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Proyecto save(Proyecto proyecto) {
        return jpaRepository.save(proyecto);
    }

    @Override
    public Optional<Proyecto> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Proyecto> findAll() {
        return jpaRepository.findAll();
    }
}
