package com.colmena.demo.creditos.infrastructure.rest.resources;

import com.colmena.demo.creditos.domain.model.valueobjects.EstadoCuota;
import com.colmena.demo.creditos.domain.model.valueobjects.TipoCuota;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CuotaResource(
        Integer periodo,
        LocalDate fechaPago,

        BigDecimal saldoInicial,
        BigDecimal interes,
        BigDecimal amortizacionCap,
        BigDecimal cuotaTotal,
        BigDecimal saldoFinal,

        BigDecimal seguroDesgravamen,
        BigDecimal seguroInmueble,
        BigDecimal comision,
        BigDecimal gastosAdm,

        BigDecimal tasaEfectivaAnual,
        BigDecimal tasaEfectivaPeriodo,
        BigDecimal flujoCaja,

        TipoCuota tipoCuota,
        EstadoCuota estadoCuotas
) {}
