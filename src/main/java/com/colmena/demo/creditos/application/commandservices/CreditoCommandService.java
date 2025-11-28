package com.colmena.demo.creditos.application.commandservices;

import com.colmena.demo.creditos.domain.model.aggregates.Credito;
import com.colmena.demo.creditos.domain.model.entities.Resultado;

public interface CreditoCommandService {
    Credito crearCredito(Credito credito);
    Resultado simularCredito(Long creditoId);
}
