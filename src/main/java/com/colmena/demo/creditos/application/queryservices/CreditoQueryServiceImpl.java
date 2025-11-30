package com.colmena.demo.creditos.application.queryservices;

import com.colmena.demo.creditos.domain.model.aggregates.Credito;
import com.colmena.demo.creditos.domain.model.entities.Cuota;
import com.colmena.demo.creditos.domain.model.entities.Resultado;
import com.colmena.demo.creditos.domain.repository.CreditoRepository;
import com.colmena.demo.creditos.domain.repository.CuotaRepository;
import com.colmena.demo.creditos.domain.repository.ResultadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditoQueryServiceImpl implements CreditoQueryService {

    private final CreditoRepository creditoRepository;
    private final CuotaRepository cuotaRepository;
    private final ResultadoRepository resultadoRepository;

    public CreditoQueryServiceImpl(CreditoRepository creditoRepository,
                                   CuotaRepository cuotaRepository,
                                   ResultadoRepository resultadoRepository) {
        this.creditoRepository = creditoRepository;
        this.cuotaRepository = cuotaRepository;
        this.resultadoRepository = resultadoRepository;
    }

    @Override
    public Credito obtenerPorId(Long id) {
        return creditoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Crédito no encontrado"));
    }

    @Override
    public List<Credito> obtenerPorCliente(Long clienteId) {
        return creditoRepository.findByClienteId(clienteId);
    }

    @Override
    public List<Cuota> obtenerCuotas(Long creditoId) {
        return cuotaRepository.findByCreditoId(creditoId);
    }

    @Override
    public Resultado obtenerResultado(Long creditoId) {
        return resultadoRepository.findByCreditoId(creditoId)
                .orElseThrow(() -> new IllegalArgumentException("Resultado no encontrado"));
    }

    @Override
    public List<Credito> obtenerTodos() {
        return creditoRepository.findAll();
    }
}
