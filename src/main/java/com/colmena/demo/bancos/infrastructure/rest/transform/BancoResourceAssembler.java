package com.colmena.demo.bancos.infrastructure.rest.transform;

import com.colmena.demo.bancos.domain.model.aggregates.Banco;
import com.colmena.demo.bancos.infrastructure.rest.resources.BancoResource;
import com.colmena.demo.bancos.infrastructure.rest.resources.TipoCambioResource;

public class BancoResourceAssembler {

    public static BancoResource toResource(Banco banco) {
        return new BancoResource(
                banco.getId(),
                banco.getNombre(),
                banco.getRuc(),
                banco.getTelefono(),
                banco.getSitioWeb(),
                banco.getMonedaBase(),
                banco.isUsaTasasNominales(),
                banco.getCapitalizacionDefault(),
                banco.getTipoCambioCompra(),
                banco.getTipoCambioVenta(),
                banco.getEstado()
        );
    }

    public static TipoCambioResource toTipoCambioResource(Banco banco) {
        return new TipoCambioResource(
                banco.getNombre(),
                banco.getMonedaBase(),
                banco.getTipoCambioCompra(),
                banco.getTipoCambioVenta()
        );
    }
}
