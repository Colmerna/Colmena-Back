package com.colmena.demo.creditos.application.commandservices;

import com.colmena.demo.creditos.domain.model.aggregates.Credito;
import com.colmena.demo.creditos.domain.model.entities.Cuota;
import com.colmena.demo.creditos.domain.model.entities.Resultado;
import com.colmena.demo.creditos.domain.repository.CreditoRepository;
import com.colmena.demo.creditos.domain.repository.CuotaRepository;
import com.colmena.demo.creditos.domain.repository.ResultadoRepository;
import com.colmena.demo.creditos.domain.service.CalculadoraCreditoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditoCommandServiceImpl implements CreditoCommandService {

    private final CreditoRepository creditoRepository;
    private final CuotaRepository cuotaRepository;
    private final ResultadoRepository resultadoRepository;
    private final CalculadoraCreditoService calculadora;

    public CreditoCommandServiceImpl(CreditoRepository creditoRepository,
                                     CuotaRepository cuotaRepository,
                                     ResultadoRepository resultadoRepository,
                                     CalculadoraCreditoService calculadora) {
        this.creditoRepository = creditoRepository;
        this.cuotaRepository = cuotaRepository;
        this.resultadoRepository = resultadoRepository;
        this.calculadora = calculadora;
    }

    @Override
    public Credito crearCredito(Credito credito) {
        return creditoRepository.save(credito);
    }

    @Override
    public Resultado simularCredito(Long creditoId) {

        Credito credito = creditoRepository.findById(creditoId)
                .orElseThrow(() -> new IllegalArgumentException("Crédito no encontrado"));


        resultadoRepository.findByCreditoId(creditoId).ifPresent(r -> {
            throw new IllegalStateException("El crédito ya fue simulado anteriormente");
        });

        List<Cuota> cuotas = calculadora.generarPlanCuotas(credito);
        cuotaRepository.saveAll(cuotas);

        Resultado resultado = calculadora.calcularResultados(credito, cuotas);



        return resultadoRepository.save(resultado);
    }
}
