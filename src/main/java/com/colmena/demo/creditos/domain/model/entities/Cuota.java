package com.colmena.demo.creditos.domain.model.entities;

import com.colmena.demo.creditos.domain.model.aggregates.Credito;
import com.colmena.demo.creditos.domain.model.valueobjects.EstadoCuota;
import com.colmena.demo.creditos.domain.model.valueobjects.TipoCuota;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "cuotas")
@NoArgsConstructor
public class Cuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCuotas")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCredito", nullable = false)
    private Credito credito;

    @Column(name = "periodo", nullable = false)
    private Integer periodo;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    // ======== DATOS PRINCIPALES ========

    @Column(name = "saldo_inicial", precision = 14, scale = 2, nullable = false)
    private BigDecimal saldoInicial;

    @Column(name = "interes", precision = 14, scale = 2, nullable = false)
    private BigDecimal interes;

    @Column(name = "amortizacion_cap", precision = 14, scale = 2, nullable = false)
    private BigDecimal amortizacionCap;

    @Column(name = "cuota_total", precision = 14, scale = 2, nullable = false)
    private BigDecimal cuotaTotal;

    @Column(name = "saldo_final", precision = 14, scale = 2, nullable = false)
    private BigDecimal saldoFinal;

    // ======== SEGUROS Y GASTOS ========

    @Column(name = "seguro_desgravamen", precision = 14, scale = 2)
    private BigDecimal seguroDesgravamen;

    @Column(name = "seguro_inmueble", precision = 14, scale = 2)
    private BigDecimal seguroInmueble;

    @Column(name = "comision", precision = 14, scale = 2)
    private BigDecimal comision;

    @Column(name = "gastos_adm", precision = 14, scale = 2)
    private BigDecimal gastosAdm;

    // ======== TASAS ========

    @Column(name = "tasa_efectiva_anual", precision = 12, scale = 8)
    private BigDecimal tasaEfectivaAnual;

    @Column(name = "tasa_efectiva_periodo", precision = 12, scale = 8)
    private BigDecimal tasaEfectivaPeriodo;

    // ======== FLUJO DE CAJA  ========

    @Column(name = "flujo_caja", precision = 14, scale = 2)
    private BigDecimal flujoCaja;

    // ======== ESTADO Y TIPO ========

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuota", nullable = false)
    private TipoCuota tipoCuota;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_cuotas", nullable = false)
    private EstadoCuota estadoCuotas;



    public Cuota(Credito credito,
                 Integer periodo,
                 LocalDate fechaPago,
                 BigDecimal saldoInicial,
                 BigDecimal interes,
                 BigDecimal amortizacionCap,
                 BigDecimal cuotaTotal,
                 BigDecimal saldoFinal,
                 BigDecimal seguroDesgravamen,
                 BigDecimal seguroInmueble,
                 BigDecimal comision,
                 BigDecimal gastosAdm,
                 BigDecimal tasaEfectivaAnual,
                 BigDecimal tasaEfectivaPeriodo,
                 BigDecimal flujoCaja,
                 TipoCuota tipoCuota,
                 EstadoCuota estadoCuotas) {

        this.credito = credito;
        this.periodo = periodo;
        this.fechaPago = fechaPago;
        this.saldoInicial = saldoInicial;
        this.interes = interes;
        this.amortizacionCap = amortizacionCap;
        this.cuotaTotal = cuotaTotal;
        this.saldoFinal = saldoFinal;
        this.seguroDesgravamen = seguroDesgravamen;
        this.seguroInmueble = seguroInmueble;
        this.comision = comision;
        this.gastosAdm = gastosAdm;
        this.tasaEfectivaAnual = tasaEfectivaAnual;
        this.tasaEfectivaPeriodo = tasaEfectivaPeriodo;
        this.flujoCaja = flujoCaja;
        this.tipoCuota = tipoCuota;
        this.estadoCuotas = estadoCuotas;
    }

}
