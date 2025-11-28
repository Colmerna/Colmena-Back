package com.colmena.demo.proyectos.application.commandservices;

import com.colmena.demo.proyectos.domain.model.aggregates.Proyecto;
import com.colmena.demo.proyectos.domain.model.commands.CreateProyectoCommand;
import com.colmena.demo.proyectos.domain.model.commands.UpdateProyectoCommand;
import com.colmena.demo.proyectos.domain.repository.ProyectoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProyectoCommandService {

    private final ProyectoRepository proyectoRepository;

    public ProyectoCommandService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    @Transactional
    public Long handle(CreateProyectoCommand command) {

        Proyecto proyecto = new Proyecto(
                command.nombre(),
                command.direccion(),
                command.distrito(),
                command.areaM2(),
                command.precio(),
                command.igv(),
                command.numHabitaciones(),
                command.tipoProyecto(),
                command.estadoProyecto(),
                command.bancoId()
        );

        return proyectoRepository.save(proyecto).getId();
    }


    @Transactional
    public void handle(UpdateProyectoCommand command) {

        Proyecto proyecto = proyectoRepository.findById(command.proyectoId())
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));

        if (command.nombre() != null) proyecto.setNombre(command.nombre());
        if (command.direccion() != null) proyecto.setDireccion(command.direccion());
        if (command.distrito() != null) proyecto.setDistrito(command.distrito());
        if (command.areaM2() != null) proyecto.setAreaM2(command.areaM2());
        if (command.precio() != null) proyecto.setPrecio(command.precio());
        if (command.igv() != null) proyecto.setIgv(command.igv());
        if (command.numHabitaciones() != null) proyecto.setNumHabitaciones(command.numHabitaciones());
        if (command.tipoProyecto() != null) proyecto.setTipoProyecto(command.tipoProyecto());
        if (command.estadoProyecto() != null) proyecto.setEstadoProyecto(command.estadoProyecto());
        if (command.bancoId() != null) proyecto.setBancoId(command.bancoId());

        proyectoRepository.save(proyecto);
    }




}
