package com.colmena.demo.creditos.infrastructure.rest.resources;

import java.math.BigDecimal;

public record ResultadoResource(
        BigDecimal cuotaBase,
        BigDecimal totalIntereses,
        BigDecimal totalAmortizacion,
        BigDecimal totalSeguroDesgravamen,
        BigDecimal totalSeguroRiesgo,
        BigDecimal totalComisiones,
        BigDecimal totalPortesGastos,
        BigDecimal totalPagado,
        BigDecimal van,
        BigDecimal tir,
        BigDecimal tea,
        BigDecimal tcea,
        BigDecimal costoTotalFinal,
        BigDecimal porcentajeIngreso,
        BigDecimal flujoTotal,
        BigDecimal saldoFinalFlujo
) {}
