// ===========================================
// CreateCreditoResource
// ===========================================
package com.colmena.demo.creditos.infrastructure.rest.resources;

import com.colmena.demo.bancos.domain.model.valueobjects.Capitalizacion;
import com.colmena.demo.bancos.domain.model.valueobjects.Moneda;
import com.colmena.demo.creditos.domain.model.valueobjects.BaseTiempo;
import com.colmena.demo.creditos.domain.model.valueobjects.GraciaTipo;
import com.colmena.demo.creditos.domain.model.valueobjects.TipoTasaInteres;

import java.math.BigDecimal;

public record CreateCreditoResource(

        Long clienteId,
        Long proyectoId,
        Long bancoId,

        // --- Datos financieros base ---
        Moneda moneda,
        TipoTasaInteres tipoTasa,
        BigDecimal tasaNominal,
        BigDecimal tasaEfectiva,
        BaseTiempo baseTiempo,
        Capitalizacion capitalizacion,

        // --- Precio vivienda / condiciones ---
        BigDecimal precioVentaActivo,
        BigDecimal cuotaInicial,
        BigDecimal bonoTecho,
        GraciaTipo graciaTipo,
        Integer graciaMeses,
        Integer plazoMeses,
        BigDecimal montoPrestamo,

        // --- Costos iniciales ---
        BigDecimal costosNotariales,
        BigDecimal costosRegistrales,
        BigDecimal tasacion,
        BigDecimal comisionEstudio,
        BigDecimal comisionActivacion,

        // --- Costos periódicos ---
        BigDecimal comisionPeriodica,
        BigDecimal portes,
        BigDecimal gastosAdmPeriodicos,
        BigDecimal porcentajeSeguroDesgravamen,
        BigDecimal porcentajeSeguroRiesgo,

        // --- Tasa de descuento ---
        BigDecimal tasaDescuentoAnual,

        // --- Frecuencia / días por año ---
        Integer frecuenciaPagoDias,
        Integer diasPorAnio
) {}
