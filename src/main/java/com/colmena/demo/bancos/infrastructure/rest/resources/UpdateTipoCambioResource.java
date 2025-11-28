package com.colmena.demo.bancos.infrastructure.rest.resources;

import java.math.BigDecimal;

public record UpdateTipoCambioResource(
        BigDecimal tipoCambioCompra,
        BigDecimal tipoCambioVenta
) {}
