package com.colmena.demo.clientes.infrastructure.rest.transform;

import com.colmena.demo.clientes.domain.model.aggregates.Cliente;
import com.colmena.demo.clientes.infrastructure.rest.resources.ClienteResource;

public class ClienteResourceAssembler {

    public static ClienteResource toResource(Cliente cliente) {
        return new ClienteResource(
                cliente.getId(),
                cliente.getDni(),
                cliente.getNombres(),
                cliente.getApellidos(),
                cliente.getTelefono(),
                cliente.getEmail(),
                cliente.getIngresoMensual(),
                cliente.getDependientes(),
                cliente.getScoreRiesgo(),
                cliente.getGastoMensualAprox(),
                cliente.getUsuarioId(),
                cliente.getSituacionLaboral(),
                cliente.getEstadoCivil()
        );
    }
}
