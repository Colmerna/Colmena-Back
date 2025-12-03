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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalculadoraCreditoServiceImpl implements CalculadoraCreditoService {

    private final ClienteRepository clienteRepository;

    public CalculadoraCreditoServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // ===========================================================
    // 1. GENERAR PLAN DE CUOTAS COMPLETO
    // ===========================================================
    @Override
    public List<Cuota> generarPlanCuotas(Credito credito) {

        List<Cuota> cuotas = new ArrayList<>();

        // === Monto del préstamo según MiVivienda / Bono Techo ===
        BigDecimal montoPrestamo = calcularMontoPrestamoConBono(credito);

        BigDecimal saldo = montoPrestamo;

        // TEM = tasa por período según base 30/360 y frecuenciaPagoDias
        BigDecimal tem = calcularTEM(credito);

        int numeroPeriodos = credito.getPlazoMeses(); // N° de cuotas

        BigDecimal cuotaBase = calcularCuotaFrancesa(saldo, tem, numeroPeriodos);

        Integer mesesGracia = credito.getGraciaMeses() != null
                ? credito.getGraciaMeses()
                : 0;

        int frecDias = credito.getFrecuenciaPagoDias() != null
                ? credito.getFrecuenciaPagoDias()
                : 30;

        int diasAnio = (credito.getDiasPorAnio() != null) ? credito.getDiasPorAnio() : 360;
        BigDecimal frecBD = BigDecimal.valueOf(frecDias);
        BigDecimal diasAnioBD = BigDecimal.valueOf(diasAnio);

        // N° cuotas por año = diasAnio / frec
        BigDecimal cuotasAnio = diasAnioBD
                .divide(frecBD, 10, RoundingMode.HALF_UP);

        // tasa del seguro de desgravamen por periodo: pSegDes/30 * frecDias
        BigDecimal tasaSegDesPeriodo = credito.getPorcentajeSeguroDesgravamen()
                .multiply(frecBD)
                .divide(new BigDecimal("30"), 10, RoundingMode.HALF_UP);

        LocalDate fechaDesembolso = credito.getFechaCreacion().toLocalDate();

        for (int n = 1; n <= numeroPeriodos; n++) {

            BigDecimal interes;
            BigDecimal amortizacion;
            BigDecimal saldoFinal;

            // ==============================
            // GRACIA
            // ==============================
            if (credito.getGraciaTipo() == GraciaTipo.GRACIA_TOTAL && n <= mesesGracia) {
                interes = BigDecimal.ZERO;
                amortizacion = BigDecimal.ZERO;
                saldoFinal = saldo;

            } else if (credito.getGraciaTipo() == GraciaTipo.GRACIA_PARCIAL && n <= mesesGracia) {
                interes = saldo.multiply(tem);
                amortizacion = BigDecimal.ZERO;
                saldoFinal = saldo;

            } else {
                interes = saldo.multiply(tem);
                amortizacion = cuotaBase.subtract(interes);
                saldoFinal = saldo.subtract(amortizacion);
            }

            // ==============================
            // SEGUROS Y GASTOS (modo Excel)
            // ==============================
            // Seguro desgravamen: pSegDes/30 * frecDias * saldo
            BigDecimal segDes = saldo
                    .multiply(tasaSegDesPeriodo)
                    .setScale(6, RoundingMode.HALF_UP);

            // Seguro riesgo fijo por período: pSegRie * PV / NCuotasAño
            BigDecimal segRie = credito.getPrecioVentaActivo()
                    .multiply(credito.getPorcentajeSeguroRiesgo())
                    .divide(cuotasAnio, 6, RoundingMode.HALF_UP);

            BigDecimal comision = credito.getComisionPeriodica();
            BigDecimal portes = credito.getPortes();
            BigDecimal gastosAdm = credito.getGastosAdmPeriodicos();

            // ==============================
            // CUOTA TOTAL
            // ==============================
            BigDecimal cuotaTotal =
                    interes.add(amortizacion)
                            .add(segDes)
                            .add(segRie)
                            .add(comision)
                            .add(portes)
                            .add(gastosAdm);

            // Fecha de vencimiento según frecuencia en días (30/360)
            LocalDate fechaVencimiento = fechaDesembolso.plusDays((long) frecDias * n);

            // ==============================
            // CREAR CUOTA
            // ==============================
            Cuota c = new Cuota(
                    credito,
                    n,
                    fechaVencimiento,
                    saldo,
                    interes,
                    amortizacion,
                    cuotaTotal,
                    saldoFinal,
                    segDes,
                    segRie,
                    comision,
                    gastosAdm,
                    calcularTEA(credito),
                    tem,                         // tasa efectiva por período
                    cuotaTotal.negate(),         // flujoCaja (sale plata del cliente)
                    (n <= mesesGracia ? TipoCuota.EN_GRACIA : TipoCuota.NORMAL),
                    EstadoCuota.PENDIENTE
            );

            cuotas.add(c);
            saldo = saldoFinal;
        }

        return cuotas;
    }


    // ===========================================================
    // 2. RESULTADOS GLOBALES
    // ===========================================================
    @Override
    public Resultado calcularResultados(Credito credito, List<Cuota> cuotas) {

        Resultado r = new Resultado(credito);

        BigDecimal tem = calcularTEM(credito);     // tasa por período
        BigDecimal tea = calcularTEA(credito);

        // TIR por período y TCEA anual (modo Excel / banco)
        BigDecimal tir = calcularTIR(credito, cuotas);
        BigDecimal tcea = calcularTCEA(credito, tir);

        // Mismo monto de préstamo que usamos en las cuotas
        BigDecimal montoPrestamo = calcularMontoPrestamoConBono(credito);

        BigDecimal cuotaBase = calcularCuotaFrancesa(
                montoPrestamo, tem, credito.getPlazoMeses()
        );

        BigDecimal totalIntereses = cuotas.stream()
                .map(Cuota::getInteres)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalAmortizacion = cuotas.stream()
                .map(Cuota::getAmortizacionCap)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPagado = cuotas.stream()
                .map(Cuota::getCuotaTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDes = cuotas.stream()
                .map(Cuota::getSeguroDesgravamen)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalRie = cuotas.stream()
                .map(Cuota::getSeguroInmueble)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalComisiones = cuotas.stream()
                .map(Cuota::getComision)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPortesGastos = credito.getPortes()
                .add(credito.getGastosAdmPeriodicos())
                .multiply(new BigDecimal(credito.getPlazoMeses()))
                .setScale(2, RoundingMode.HALF_UP);

        // ==============================
        // SALDO A FINANCIAR Y COSTOS INICIALES (modo Excel + Bono Techo)
        // ==============================
        BigDecimal saldoAFinanciar = calcularSaldoAFinanciarConBono(credito);
        BigDecimal costosIniciales = calcularCostosIniciales(credito);
        BigDecimal netoInicial = saldoAFinanciar.subtract(costosIniciales);

        // PORCENTAJE DE INGRESO
        BigDecimal porcentajeIngreso = null;

        Cliente cliente = clienteRepository.findById(credito.getClienteId()).orElse(null);

        if (cliente != null && cliente.getIngresoMensual() != null &&
                cliente.getIngresoMensual().compareTo(BigDecimal.ZERO) > 0) {

            porcentajeIngreso = cuotaBase
                    .divide(cliente.getIngresoMensual(), 6, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
        }

        // FLUJOS (visión cliente simple): salida inicial + pagos
        BigDecimal flujoTotal = netoInicial.negate().add(totalPagado);
        BigDecimal saldoFinalFlujo = flujoTotal;

        // VAN desde el punto de vista del banco (flujo0 = montoPrestamo, flujos = -cuotas)
        BigDecimal van = calcularVAN(credito, cuotas, credito.getTasaDescuentoAnual());

        // ASIGNAR TODOS LOS VALORES A RESULTADO
        r.setTea(tea);
        r.setTcea(tcea);
        r.setTir(tir);
        r.setVan(van);

        r.setCuotaBase(cuotaBase);
        r.setTotalIntereses(totalIntereses);
        r.setTotalAmortizacion(totalAmortizacion);
        r.setTotalSeguroDesgravamen(totalDes);
        r.setTotalSeguroRiesgo(totalRie);
        r.setTotalComisiones(totalComisiones);
        r.setTotalPortesGastos(totalPortesGastos);
        r.setTotalPagado(totalPagado);

        r.setCostoTotalFinal(totalPagado);
        r.setPorcentajeIngreso(porcentajeIngreso);

        r.setFlujoTotal(flujoTotal);
        r.setSaldoFinalFlujo(saldoFinalFlujo);

        return r;
    }


    // ===========================================================
    // 3. FORMULAS FINANCIERAS
    // ===========================================================
    private BigDecimal calcularTEA(Credito credito) {

        if (credito.getTipoTasa() == TipoTasaInteres.NOMINAL) {

            BigDecimal r = credito.getTasaNominal()
                    .divide(new BigDecimal(credito.getCapitalizacion().getPeriodsPerYear()), 10, RoundingMode.HALF_UP)
                    .add(BigDecimal.ONE);

            return r.pow(credito.getCapitalizacion().getPeriodsPerYear())
                    .subtract(BigDecimal.ONE);
        }

        return credito.getTasaEfectiva();
    }

    /**
     * TEM/Tasa por período según base 30/360 y frecuenciaPagoDias.
     */
    private BigDecimal calcularTEM(Credito credito) {

        BigDecimal tea = calcularTEA(credito);  // TEA anual

        int diasAnio = (credito.getDiasPorAnio() != null) ? credito.getDiasPorAnio() : 360;
        int frecDias = (credito.getFrecuenciaPagoDias() != null) ? credito.getFrecuenciaPagoDias() : 30;

        double periodsPerYear = (double) diasAnio / frecDias; // p.ej. 360/90 = 4

        double tem = Math.pow(1 + tea.doubleValue(), 1.0 / periodsPerYear) - 1;

        return new BigDecimal(tem).setScale(10, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularCuotaFrancesa(BigDecimal capital, BigDecimal tem, int numeroPeriodos) {

        BigDecimal unoMasTem = BigDecimal.ONE.add(tem);
        BigDecimal pow = unoMasTem.pow(numeroPeriodos);

        BigDecimal numerador = capital.multiply(tem).multiply(pow);
        BigDecimal denominador = pow.subtract(BigDecimal.ONE);

        return numerador.divide(denominador, 6, RoundingMode.HALF_UP);
    }


    // ===========================================================
    // 4. VAN / TIR / TCEA
    // ===========================================================
    private BigDecimal calcularVAN(Credito credito, List<Cuota> cuotas, BigDecimal tasaDescuentoAnual) {

        if (tasaDescuentoAnual == null)
            tasaDescuentoAnual = new BigDecimal("0.10");

        BigDecimal tem = convertirEfectivaAPeriodo(credito, tasaDescuentoAnual);

        // Flujo 0 igual que Excel: Monto del préstamo calculado (positivo)
        BigDecimal montoPrestamo = calcularMontoPrestamoConBono(credito);
        BigDecimal van = montoPrestamo;

        // Flujos de cada período: -CuotaTotal / (1+tem)^n
        for (Cuota c : cuotas) {
            BigDecimal descuento = BigDecimal.ONE.add(tem).pow(c.getPeriodo());
            BigDecimal vp = c.getCuotaTotal()
                    .divide(descuento, 6, RoundingMode.HALF_UP);
            van = van.subtract(vp);
        }

        return van;
    }


    private BigDecimal convertirEfectivaAPeriodo(Credito credito, BigDecimal tea) {

        int diasAnio = (credito.getDiasPorAnio() != null) ? credito.getDiasPorAnio() : 360;
        int frecDias = (credito.getFrecuenciaPagoDias() != null) ? credito.getFrecuenciaPagoDias() : 30;

        double periodsPerYear = (double) diasAnio / frecDias;

        double t = Math.pow(1 + tea.doubleValue(), 1.0 / periodsPerYear) - 1;
        return new BigDecimal(t).setScale(10, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularTIR(Credito credito, List<Cuota> cuotas) {

        // Flujo 0 igual que Excel: monto del préstamo calculado (positivo)
        double flujo0 = calcularMontoPrestamoConBono(credito).doubleValue();

        double lower = 0.0;
        double upper = 0.5;   // 50% por período máximo
        double tir = 0;

        for (int i = 0; i < 80; i++) {

            tir = (lower + upper) / 2.0;

            // NPV con CF0 positivo y cuotas negativas
            double npv = flujo0;

            for (Cuota c : cuotas) {
                double cf = -c.getCuotaTotal().doubleValue();  // flujo de salida
                npv += cf / Math.pow(1 + tir, c.getPeriodo());
            }

            if (Math.abs(npv) < 1e-6) {
                break; // suficientemente cerca de 0
            }

            // OJO: función NPV crece con la tasa en este set de flujos
            if (npv > 0) {
                upper = tir;   // estamos por encima de la raíz
            } else {
                lower = tir;   // estamos por debajo de la raíz
            }
        }

        return BigDecimal.valueOf(tir).setScale(8, RoundingMode.HALF_UP);
    }

    /**
     * TCEA anual a partir de TIR por período,
     * usando número de períodos por año según 30/360.
     */
    private BigDecimal calcularTCEA(Credito credito, BigDecimal tirPorPeriodo) {
        if (tirPorPeriodo == null) return BigDecimal.ZERO;

        int diasAnio = (credito.getDiasPorAnio() != null) ? credito.getDiasPorAnio() : 360;
        int frecDias = (credito.getFrecuenciaPagoDias() != null) ? credito.getFrecuenciaPagoDias() : 30;

        double periodsPerYear = (double) diasAnio / frecDias;

        double t = Math.pow(1 + tirPorPeriodo.doubleValue(), periodsPerYear) - 1;
        return new BigDecimal(t).setScale(8, RoundingMode.HALF_UP);
    }

    // ===========================================================
    // 5. HELPERS BONO TECHO / COSTOS INICIALES
    // ===========================================================
    /** Bono Techo seguro, nunca null */
    private BigDecimal obtenerBono(Credito credito) {
        return credito.getBonoTecho() != null ? credito.getBonoTecho() : BigDecimal.ZERO;
    }

    /** Suma de todos los costos/gastos iniciales financiados */
    private BigDecimal calcularCostosIniciales(Credito credito) {
        BigDecimal ci = BigDecimal.ZERO;
        if (credito.getCostosNotariales() != null) ci = ci.add(credito.getCostosNotariales());
        if (credito.getCostosRegistrales() != null) ci = ci.add(credito.getCostosRegistrales());
        if (credito.getTasacion() != null) ci = ci.add(credito.getTasacion());
        if (credito.getComisionEstudio() != null) ci = ci.add(credito.getComisionEstudio());
        if (credito.getComisionActivacion() != null) ci = ci.add(credito.getComisionActivacion());
        return ci;
    }

    /** Saldo a financiar: PV - cuota inicial - bono techo (no negativo) */
    private BigDecimal calcularSaldoAFinanciarConBono(Credito credito) {
        BigDecimal bono = obtenerBono(credito);
        BigDecimal saldo = credito.getPrecioVentaActivo()
                .subtract(credito.getCuotaInicial())
                .subtract(bono);

        if (saldo.compareTo(BigDecimal.ZERO) < 0) {
            saldo = BigDecimal.ZERO;
        }
        return saldo;
    }

    /** Monto del préstamo total financiado: saldo a financiar + costos iniciales */
    private BigDecimal calcularMontoPrestamoConBono(Credito credito) {
        BigDecimal saldoAFinanciar = calcularSaldoAFinanciarConBono(credito);
        BigDecimal costosIniciales = calcularCostosIniciales(credito);
        return saldoAFinanciar.add(costosIniciales);
    }
}
