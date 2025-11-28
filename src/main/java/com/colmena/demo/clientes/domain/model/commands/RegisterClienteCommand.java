package com.colmena.demo.clientes.domain.model.commands;

import com.colmena.demo.clientes.domain.model.valueobjects.EstadoCivil;
import com.colmena.demo.clientes.domain.model.valueobjects.SituacionLaboral;

import java.math.BigDecimal;

public record RegisterClienteCommand(
        String dni,
        String nombres,
        String apellidos,
        String telefono,
        String email,
        BigDecimal ingresoMensual,
        Integer dependientes,
        BigDecimal scoreRiesgo,
        BigDecimal gastoMensualAprox,
        Long usuarioId,
        SituacionLaboral situacionLaboral,
        EstadoCivil estadoCivil
) {}
