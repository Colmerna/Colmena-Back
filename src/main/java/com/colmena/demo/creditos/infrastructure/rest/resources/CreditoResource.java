package com.colmena.demo.creditos.infrastructure.rest.resources;

import com.colmena.demo.bancos.domain.model.valueobjects.Capitalizacion;
import com.colmena.demo.bancos.domain.model.valueobjects.Moneda;
import com.colmena.demo.creditos.domain.model.valueobjects.BaseTiempo;
import com.colmena.demo.creditos.domain.model.valueobjects.EstadoCredito;
import com.colmena.demo.creditos.domain.model.valueobjects.GraciaTipo;
import com.colmena.demo.creditos.domain.model.valueobjects.TipoTasaInteres;

import java.math.BigDecimal;

public record CreditoResource(
        Long id,
        Long clienteId,
        Long proyectoId,
        Long bancoId,
        Moneda moneda,
        TipoTasaInteres tipoTasa,
        BigDecimal tasaNominal,
        BigDecimal tasaEfectiva,
        BaseTiempo baseTiempo,
        Capitalizacion capitalizacion,
        BigDecimal cuotaInicial,
        BigDecimal bonoTecho,
        GraciaTipo graciaTipo,
        Integer graciaMeses,
        Integer plazoMeses,
        BigDecimal montoPrestamo,
        EstadoCredito estadoCredito
) {}
