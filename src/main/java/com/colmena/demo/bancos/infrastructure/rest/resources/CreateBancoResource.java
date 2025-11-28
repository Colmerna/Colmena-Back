package com.colmena.demo.bancos.infrastructure.rest.resources;

import com.colmena.demo.bancos.domain.model.valueobjects.Moneda;
import com.colmena.demo.bancos.domain.model.valueobjects.Capitalizacion;
import com.colmena.demo.bancos.domain.model.valueobjects.EstadoBanco;

import java.math.BigDecimal;

public record CreateBancoResource(
        String nombre,
        String ruc,
        String telefono,
        String sitioWeb,
        Moneda monedaBase,
        boolean usaTasasNominales,
        Capitalizacion capitalizacionDefault,
        BigDecimal tipoCambioCompra,
        BigDecimal tipoCambioVenta,
        EstadoBanco estado
) {}
