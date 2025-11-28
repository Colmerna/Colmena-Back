package com.colmena.demo.proyectos.application.queryservices;

import com.colmena.demo.proyectos.domain.model.aggregates.Proyecto;
import com.colmena.demo.proyectos.domain.model.queries.GetAllProyectosQuery;
import com.colmena.demo.proyectos.domain.model.queries.GetProyectoByIdQuery;
import com.colmena.demo.proyectos.domain.repository.ProyectoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoQueryService {

    private final ProyectoRepository proyectoRepository;

    public ProyectoQueryService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    public Optional<Proyecto> handle(GetProyectoByIdQuery query) {
        return proyectoRepository.findById(query.proyectoId());
    }

    public List<Proyecto> handle(GetAllProyectosQuery query) {

        return proyectoRepository.findAll();

    }
}
