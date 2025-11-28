package com.colmena.demo.bancos.infrastructure.rest.resources;

import com.colmena.demo.bancos.domain.model.valueobjects.Moneda;

import java.math.BigDecimal;

public record TipoCambioResource(
        String banco,
        Moneda monedaBase,
        BigDecimal compra,
        BigDecimal venta
) {}
