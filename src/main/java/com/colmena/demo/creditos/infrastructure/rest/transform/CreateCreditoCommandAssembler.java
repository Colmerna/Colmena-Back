// ===========================================
// CreateCreditoCommandAssembler
// ===========================================
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
                r.precioVentaActivo(),
                r.cuotaInicial(),
                r.bonoTecho(),
                r.graciaTipo(),
                r.graciaMeses(),
                r.plazoMeses(),
                r.montoPrestamo(),
                r.costosNotariales(),
                r.costosRegistrales(),
                r.tasacion(),
                r.comisionEstudio(),
                r.comisionActivacion(),
                r.comisionPeriodica(),
                r.portes(),
                r.gastosAdmPeriodicos(),
                r.porcentajeSeguroDesgravamen(),
                r.porcentajeSeguroRiesgo(),
                r.tasaDescuentoAnual(),
                r.frecuenciaPagoDias(),
                r.diasPorAnio(),
                EstadoCredito.PENDIENTE
        );
    }
}
