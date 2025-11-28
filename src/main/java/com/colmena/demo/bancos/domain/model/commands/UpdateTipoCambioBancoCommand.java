package com.colmena.demo.bancos.domain.model.commands;

import java.math.BigDecimal;

public record UpdateTipoCambioBancoCommand(
        Long bancoId,
        BigDecimal tipoCambioCompra,
        BigDecimal tipoCambioVenta
) {}
