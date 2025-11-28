package com.colmena.demo.creditos.infrastructure.rest.transform;

import com.colmena.demo.creditos.domain.model.aggregates.Credito;
import com.colmena.demo.creditos.domain.model.valueobjects.EstadoCredito;
import com.colmena.demo.creditos.infrastructure.rest.resources.CreateCreditoResource;

public class CreateCreditoCommandAssembler {

    public static Credito toEntity(CreateCreditoResource r) {

        return new Credito(
                r.clienteId(),
                r.proyectoId(),
                r.bancoId(),
                r.moneda(),
                r.tipoTasa(),
                r.tasaNominal(),
                r.tasaEfectiva(),
                r.baseTiempo(),
                r.capitalizacion(),
                r.cuotaInicial(),
                r.bonoTecho(),
                r.graciaTipo(),
                r.graciaMeses(),
                r.plazoMeses(),
                r.montoPrestamo(),
                EstadoCredito.PENDIENTE
        );
    }
}
