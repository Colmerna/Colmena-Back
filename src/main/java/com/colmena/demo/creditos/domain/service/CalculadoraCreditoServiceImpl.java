package com.colmena.demo.creditos.domain.service;

import com.colmena.demo.clientes.domain.model.aggregates.Cliente;
import com.colmena.demo.clientes.domain.repository.ClienteRepository;
import com.colmena.demo.creditos.domain.model.aggregates.Credito;
import com.colmena.demo.creditos.domain.model.entities.Cuota;
import com.colmena.demo.creditos.domain.model.entities.Resultado;
import com.colmena.demo.creditos.domain.model.valueobjects.EstadoCuota;
import com.colmena.demo.creditos.domain.model.valueobjects.GraciaTipo;
import com.colmena.demo.creditos.domain.model.valueobjects.TipoCuota;
import com.colmena.demo.creditos.domain.model.valueobjects.TipoTasaInteres;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalculadoraCreditoServiceImpl implements CalculadoraCreditoService {

    private final ClienteRepository clienteRepository;

    public CalculadoraCreditoServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    // 1. GENERAR LAS CUOTAS COMPLETAS

    @Override
    public List<Cuota> generarPlanCuotas(Credito credito) {

        List<Cuota> cuotas = new ArrayList<>();

        BigDecimal saldo = credito.getMontoPrestamo();
        BigDecimal tem = calcularTEM(credito);
        int plazo = credito.getPlazoMeses();

        BigDecimal cuotaBase = calcularCuotaFrancesa(saldo, tem, plazo);

        for (int periodo = 1; periodo <= plazo; periodo++) {

            BigDecimal interes;
            BigDecimal amortizacion;
            BigDecimal saldoFinal;

            Integer graciaMeses = credito.getGraciaMeses() != null ? credito.getGraciaMeses() : 0;

            // -----------------------------
            // MANEJO DE GRACIA
            // -----------------------------
            if (credito.getGraciaTipo() == GraciaTipo.GRACIA_TOTAL &&
                    periodo <= graciaMeses) {

                interes = BigDecimal.ZERO;
                amortizacion = BigDecimal.ZERO;
                saldoFinal = saldo;

            } else if (credito.getGraciaTipo() == GraciaTipo.GRACIA_PARCIAL &&
                    periodo <= graciaMeses) {

                interes = saldo.multiply(tem);
                amortizacion = BigDecimal.ZERO;
                saldoFinal = saldo;

            } else {

                interes = saldo.multiply(tem);
                amortizacion = cuotaBase.subtract(interes);
                saldoFinal = saldo.subtract(amortizacion);
            }

            // ----------------------------------------
            // SEGUROS Y GASTOS
            // ----------------------------------------
            BigDecimal segDes = calcularSeguroDesgravamen(saldo);
            BigDecimal segInm = calcularSeguroInmueble(credito.getMontoPrestamo());
            BigDecimal comision = calcularComision();
            BigDecimal gastosAdm = calcularGastoAdministrativo();

            // ----------------------------------------
            // CUOTA TOTAL
            // ----------------------------------------
            BigDecimal cuotaTotal = interes
                    .add(amortizacion)
                    .add(segDes)
                    .add(segInm)
                    .add(comision)
                    .add(gastosAdm);

            Cuota cuota = new Cuota(
                    credito,
                    periodo,
                    credito.getFechaCreacion().toLocalDate().plusMonths(periodo),
                    saldo,
                    interes,
                    amortizacion,
                    cuotaTotal,
                    saldoFinal,
                    segDes,
                    segInm,
                    comision,
                    gastosAdm,
                    periodo <= graciaMeses
                            ? TipoCuota.EN_GRACIA
                            : TipoCuota.NORMAL,
                    EstadoCuota.PENDIENTE
            );

            cuotas.add(cuota);
            saldo = saldoFinal;
        }

        return cuotas;
    }

    // ===========================================================
    // 2. CALCULAR RESULTADOS GLOBALES (TCEA, VAN, TIR…)
    // ===========================================================
    @Override
    public Resultado calcularResultados(Credito credito, List<Cuota> cuotas) {

        Resultado r = new Resultado(credito);

        BigDecimal tem = calcularTEM(credito);
        BigDecimal tir = calcularTIR(credito, cuotas);     // TIR periódica (mensual)
        BigDecimal tcea = calcularTCEA(tir);               // TCEA anual desde TIR mensual
        BigDecimal van = calcularVAN(credito, cuotas, tem);

        BigDecimal totalIntereses = cuotas.stream()
                .map(Cuota::getInteres)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPagado = cuotas.stream()
                .map(Cuota::getCuotaTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal costoFinal = totalPagado;

        BigDecimal tea = calcularTEA(credito);
        BigDecimal cuotaBase = calcularCuotaFrancesa(
                credito.getMontoPrestamo(), tem, credito.getPlazoMeses()
        );

        // ----------------------------------------------------
        // PORCENTAJE DE INGRESO DEL CLIENTE
        // ----------------------------------------------------
        BigDecimal porcentajeIngreso = null;
        if (credito.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(credito.getClienteId())
                    .orElse(null);

            if (cliente != null &&
                    cliente.getIngresoMensual() != null &&
                    cliente.getIngresoMensual().compareTo(BigDecimal.ZERO) > 0) {

                porcentajeIngreso = cuotaBase
                        .divide(cliente.getIngresoMensual(), 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("100"));
                        // 32.50 = 32.50%

            }
        }

        r.setTea(tea);
        r.setTcea(tcea);
        r.setTirCliente(tir);
        r.setCuotaBase(cuotaBase);
        r.setTotalIntereses(totalIntereses);
        r.setTotalPagado(totalPagado);
        r.setCostoTotalFinal(costoFinal);
        r.setVanCliente(van);
        r.setPorcentajeIngreso(porcentajeIngreso);

        return r;
    }


    // ===========================================================
    // 3. FORMULAS FINANCIERAS
    // ===========================================================

    private BigDecimal calcularTEA(Credito credito) {

        if (credito.getTipoTasa() == TipoTasaInteres.NOMINAL) {

            if (credito.getTasaNominal() == null)
                throw new IllegalArgumentException("La tasa nominal no puede ser null cuando tipoTasa = NOMINAL");

            int m = credito.getCapitalizacion().getPeriodsPerYear();
            if (m <= 0)
                throw new IllegalArgumentException("La capitalización debe tener al menos un periodo por año");

            return convertirNominalAEfectiva(credito.getTasaNominal(), m);
        }

        if (credito.getTasaEfectiva() == null)
            throw new IllegalArgumentException("La tasa efectiva no puede ser null cuando tipoTasa = EFECTIVA");

        return credito.getTasaEfectiva();
    }

    private BigDecimal convertirNominalAEfectiva(BigDecimal tasaNominal, int capitalizacion) {

        BigDecimal base = tasaNominal
                .divide(new BigDecimal(capitalizacion), 10, RoundingMode.HALF_UP)
                .add(BigDecimal.ONE);

        return base.pow(capitalizacion).subtract(BigDecimal.ONE);
    }

    private BigDecimal convertirEfectivaAMensual(BigDecimal tea) {

        double temDouble =
                Math.pow(BigDecimal.ONE.add(tea).doubleValue(), 1.0 / 12.0) - 1;

        return new BigDecimal(temDouble)
                .setScale(10, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularTEM(Credito credito) {

        BigDecimal tea = calcularTEA(credito);
        return convertirEfectivaAMensual(tea);
    }

    private BigDecimal calcularCuotaFrancesa(BigDecimal capital, BigDecimal tem, int plazo) {

        BigDecimal unoMasTem = BigDecimal.ONE.add(tem);
        BigDecimal potencia = unoMasTem.pow(plazo);

        BigDecimal numerador = capital.multiply(tem).multiply(potencia);
        BigDecimal denominador = potencia.subtract(BigDecimal.ONE);

        return numerador.divide(denominador, 6, RoundingMode.HALF_UP);
    }


    // ===========================================================
    // 4. SEGUROS, COMISIONES, GASTOS
    // ===========================================================

    private BigDecimal calcularSeguroDesgravamen(BigDecimal saldo) {
        return saldo.multiply(new BigDecimal("0.0005")); // 0.05% mensual
    }

    private BigDecimal calcularSeguroInmueble(BigDecimal precioVivienda) {
        return precioVivienda
                .multiply(new BigDecimal("0.004"))
                .divide(new BigDecimal("12"), 6, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularComision() {
        return new BigDecimal("3.00");
    }

    private BigDecimal calcularGastoAdministrativo() {
        return new BigDecimal("10.00");
    }


    // ===========================================================
    // 5. VAN, TIR, TCEA
    // ===========================================================

    private BigDecimal calcularVAN(Credito credito, List<Cuota> cuotas, BigDecimal tem) {

        if (cuotas == null || cuotas.isEmpty())
            throw new IllegalArgumentException("No se puede calcular VAN sin cuotas");

        BigDecimal van = credito.getMontoPrestamo().negate();

        for (Cuota c : cuotas) {
            BigDecimal descuento = BigDecimal.ONE.add(tem).pow(c.getPeriodo());
            BigDecimal vp = c.getCuotaTotal()
                    .divide(descuento, 6, RoundingMode.HALF_UP);
            van = van.add(vp);
        }

        return van;
    }

    private BigDecimal calcularTIR(Credito credito, List<Cuota> cuotas) {

        if (cuotas == null || cuotas.isEmpty())
            throw new IllegalArgumentException("No se puede calcular TIR sin cuotas");

        double lower = 0.0;
        double upper = 2.0;
        double tir = 0;

        for (int i = 0; i < 100; i++) {
            tir = (lower + upper) / 2.0;

            double npv = -credito.getMontoPrestamo().doubleValue();

            for (Cuota c : cuotas) {
                npv += c.getCuotaTotal().doubleValue() /
                        Math.pow(1 + tir, c.getPeriodo());
            }

            if (npv > 0) {
                lower = tir;
            } else {
                upper = tir;
            }
        }

        return BigDecimal.valueOf(tir);
    }

    private BigDecimal calcularTCEA(BigDecimal tir) {
        double t = Math.pow(1 + tir.doubleValue(), 12) - 1;
        return new BigDecimal(t).setScale(6, RoundingMode.HALF_UP);
    }
}
