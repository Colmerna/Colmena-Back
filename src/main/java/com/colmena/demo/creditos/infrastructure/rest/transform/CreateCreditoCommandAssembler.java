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
                // costos iniciales
                r.costosNotariales(),
                r.costosRegistrales(),
                r.tasacion(),
                r.comisionEstudio(),
                r.comisionActivacion(),
                // costos periódicos
                r.comisionPeriodica(),
                r.portes(),
                r.gastosAdmPeriodicos(),
                r.porcentajeSeguroDesgravamen(),
                r.porcentajeSeguroRiesgo(),
                // tasa descuento
                r.tasaDescuentoAnual(),
                // frecuencia
                r.frecuenciaPagoDias(),
                r.diasPorAnio(),
                // estado
                EstadoCredito.PENDIENTE
        );
    }
}
