package com.colmena.demo.bancos.domain.model.commands;

import com.colmena.demo.bancos.domain.model.valueobjects.EstadoBanco;
import com.colmena.demo.bancos.domain.model.valueobjects.Moneda;
import com.colmena.demo.bancos.domain.model.valueobjects.Capitalizacion;

import java.math.BigDecimal;

public record CreateBancosCommand(
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
