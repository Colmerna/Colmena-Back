package com.colmena.demo.creditos.infrastructure.rest.transform;

import com.colmena.demo.creditos.domain.model.aggregates.Credito;
import com.colmena.demo.creditos.infrastructure.rest.resources.CreditoResource;

public class CreditoResourceAssembler {

    public static CreditoResource toResource(Credito c) {
        return new CreditoResource(
                c.getId(),
                c.getClienteId(),
                c.getProyectoId(),
                c.getBancoId(),
                c.getMoneda(),
                c.getTipoTasa(),
                c.getTasaNominal(),
                c.getTasaEfectiva(),
                c.getBaseTiempo(),
                c.getCapitalizacion(),
                c.getCuotaInicial(),
                c.getBonoTecho(),
                c.getGraciaTipo(),
                c.getGraciaMeses(),
                c.getPlazoMeses(),
                c.getMontoPrestamo(),
                c.getEstadoCredito()
        );
    }
}
