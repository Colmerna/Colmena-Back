package com.colmena.demo.creditos.infrastructure.rest.transform;

import com.colmena.demo.creditos.domain.model.entities.Cuota;
import com.colmena.demo.creditos.infrastructure.rest.resources.CuotaResource;

public class CuotaResourceAssembler {

    public static CuotaResource toResource(Cuota c) {
        return new CuotaResource(
                c.getPeriodo(),
                c.getCuotaTotal(),      // antes getCuota()
                c.getInteres(),
                c.getAmortizacionCap(), // asegúrate que así se llama el getter
                c.getSeguroDesgravamen(),
                c.getSeguroInmueble(),
                c.getComision(),
                c.getGastosAdm(),       // antes getGastosAdministrativos()
                c.getSaldoFinal()
        );
    }
}