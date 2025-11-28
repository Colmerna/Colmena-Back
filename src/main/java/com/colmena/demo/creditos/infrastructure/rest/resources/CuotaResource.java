package com.colmena.demo.creditos.infrastructure.rest.resources;

import java.math.BigDecimal;

public record CuotaResource(
        Integer periodo,
        BigDecimal cuota,
        BigDecimal interes,
        BigDecimal amortizacion,
        BigDecimal seguroDesgravamen,
        BigDecimal seguroInmueble,
        BigDecimal comision,
        BigDecimal gastos,
        BigDecimal saldo
) {}
