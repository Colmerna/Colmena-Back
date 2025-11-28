package com.colmena.demo.clientes.application.commandservices;

import com.colmena.demo.clientes.domain.model.aggregates.Cliente;
import com.colmena.demo.clientes.domain.model.commands.RegisterClienteCommand;
import com.colmena.demo.clientes.domain.model.commands.UpdateClienteCommand;
import com.colmena.demo.clientes.domain.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteCommandService {

    private final ClienteRepository clienteRepository;

    public ClienteCommandService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Long handle(RegisterClienteCommand command) {

        if (clienteRepository.existsByDni(command.dni()))
            throw new IllegalArgumentException("Ya existe un cliente con ese DNI");

        if (command.email() != null && !command.email().isBlank()
                && clienteRepository.existsByEmail(command.email()))
            throw new IllegalArgumentException("Ya existe un cliente con ese email");

        Cliente cliente = new Cliente(
                command.dni(),
                command.nombres(),
                command.apellidos(),
                command.telefono(),
                command.email(),
                command.ingresoMensual(),
                command.dependientes(),
                command.scoreRiesgo(),
                command.gastoMensualAprox(),
                command.usuarioId(),
                command.situacionLaboral(),
                command.estadoCivil()
        );

        return clienteRepository.save(cliente).getId();
    }

    public void handle(UpdateClienteCommand command) {

        Cliente cliente = clienteRepository.findById(command.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        // Como no tenemos setters, podrías:
        // - agregar métodos de actualización en el agregado
        // o
        // - por simplicidad en este proyecto, usar reflexión o cambiar el diseño.
        // Para hacerlo simple, aquí cambio algunos campos con setters
        // (si quieres, luego convertimos a métodos tipo cliente.actualizarDatos(...))

        if (command.telefono() != null) cliente.setTelefono(command.telefono());
        if (command.email() != null) cliente.setEmail(command.email());
        if (command.ingresoMensual() != null) cliente.setIngresoMensual(command.ingresoMensual());
        if (command.dependientes() != null) cliente.setDependientes(command.dependientes());
        if (command.scoreRiesgo() != null) cliente.setScoreRiesgo(command.scoreRiesgo());
        if (command.gastoMensualAprox() != null) cliente.setGastoMensualAprox(command.gastoMensualAprox());
        if (command.situacionLaboral() != null) cliente.setSituacionLaboral(command.situacionLaboral());
        if (command.estadoCivil() != null) cliente.setEstadoCivil(command.estadoCivil());

        clienteRepository.save(cliente);
    }
}
