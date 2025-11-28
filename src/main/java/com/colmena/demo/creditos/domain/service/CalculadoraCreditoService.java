package com.colmena.demo.creditos.domain.service;

import com.colmena.demo.creditos.domain.model.aggregates.Credito;
import com.colmena.demo.creditos.domain.model.entities.Cuota;
import com.colmena.demo.creditos.domain.model.entities.Resultado;

import java.util.List;

public interface CalculadoraCreditoService {

    List<Cuota> generarPlanCuotas(Credito credito);

    Resultado calcularResultados(Credito credito, List<Cuota> cuotas);
}
