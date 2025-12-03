package com.colmena.demo.creditos.infrastructure.rest.transform;

import com.colmena.demo.creditos.domain.model.entities.Resultado;
import com.colmena.demo.creditos.infrastructure.rest.resources.ResultadoResource;

public class ResultadoResourceAssembler {

    public static ResultadoResource toResource(Resultado r) {
        return new ResultadoResource(
                r.getCuotaBase(),
                r.getTotalIntereses(),
                r.getTotalAmortizacion(),
                r.getTotalSeguroDesgravamen(),
                r.getTotalSeguroRiesgo(),
                r.getTotalComisiones(),
                r.getTotalPortesGastos(),
                r.getTotalPagado(),
                r.getVan(),
                r.getTir(),
                r.getTea(),
                r.getTcea(),
                r.getCostoTotalFinal(),
                r.getPorcentajeIngreso(),
                r.getFlujoTotal(),
                r.getSaldoFinalFlujo()
        );
    }
}
