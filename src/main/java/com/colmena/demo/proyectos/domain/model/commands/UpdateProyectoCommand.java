package com.colmena.demo.proyectos.domain.model.commands;

import com.colmena.demo.proyectos.domain.model.valueobjects.EstadoProyecto;
import com.colmena.demo.proyectos.domain.model.valueobjects.TipoProyecto;

import java.math.BigDecimal;

public record UpdateProyectoCommand(
        Long proyectoId,
        String nombre,
        String direccion,
        String distrito,
        Double areaM2,
        BigDecimal precio,
        BigDecimal igv,
        Integer numHabitaciones,
        TipoProyecto tipoProyecto,
        EstadoProyecto estadoProyecto,
        Long bancoId
) {}
