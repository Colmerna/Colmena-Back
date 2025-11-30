package com.colmena.demo.creditos.application.queryservices;

import com.colmena.demo.creditos.domain.model.aggregates.Credito;
import com.colmena.demo.creditos.domain.model.entities.Cuota;
import com.colmena.demo.creditos.domain.model.entities.Resultado;

import java.util.List;

public interface CreditoQueryService {

    Credito obtenerPorId(Long id);
    List<Credito> obtenerPorCliente(Long clienteId);
    List<Cuota> obtenerCuotas(Long creditoId);
    Resultado obtenerResultado(Long creditoId);
    List<Credito> obtenerTodos();
}
