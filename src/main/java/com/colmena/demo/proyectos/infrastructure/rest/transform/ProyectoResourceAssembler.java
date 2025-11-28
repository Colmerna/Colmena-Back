package com.colmena.demo.proyectos.infrastructure.rest.transform;

import com.colmena.demo.proyectos.domain.model.aggregates.Proyecto;
import com.colmena.demo.proyectos.infrastructure.rest.resources.ProyectoResource;

public class ProyectoResourceAssembler {

    public static ProyectoResource toResource(Proyecto proyecto) {
        return new ProyectoResource(
                proyecto.getId(),
                proyecto.getNombre(),
                proyecto.getDireccion(),
                proyecto.getDistrito(),
                proyecto.getAreaM2(),
                proyecto.getPrecio(),
                proyecto.getIgv(),
                proyecto.getNumHabitaciones(),
                proyecto.getTipoProyecto(),
                proyecto.getEstadoProyecto(),
                proyecto.getBancoId()
        );
    }
}
