package com.colmena.demo.creditos.infrastructure.rest.resources;

import java.math.BigDecimal;

public record ResultadoResource(
        BigDecimal cuotaBase,
        BigDecimal totalIntereses,
        BigDecimal totalPagado,
        BigDecimal vanCliente,
        BigDecimal tirCliente,
        BigDecimal tea,
        BigDecimal tcea,
        BigDecimal costoTotalFinal,
        BigDecimal porcentajeIngreso
) {}
