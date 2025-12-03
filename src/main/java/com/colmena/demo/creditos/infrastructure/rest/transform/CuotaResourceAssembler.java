package com.colmena.demo.creditos.infrastructure.rest.transform;

import com.colmena.demo.creditos.domain.model.entities.Cuota;
import com.colmena.demo.creditos.infrastructure.rest.resources.CuotaResource;

public class CuotaResourceAssembler {

    public static CuotaResource toResource(Cuota c) {
        return new CuotaResource(
                c.getPeriodo(),
                c.getFechaPago(),
                c.getSaldoInicial(),
                c.getInteres(),
                c.getAmortizacionCap(),
                c.getCuotaTotal(),
                c.getSaldoFinal(),
                c.getSeguroDesgravamen(),
                c.getSeguroInmueble(),
                c.getComision(),
                c.getGastosAdm(),
                c.getTasaEfectivaAnual(),
                c.getTasaEfectivaPeriodo(),
                c.getFlujoCaja(),
                c.getTipoCuota(),
                c.getEstadoCuotas()
        );
    }
}
