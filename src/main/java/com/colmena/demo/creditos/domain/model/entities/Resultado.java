package com.colmena.demo.creditos.domain.model.entities;

import com.colmena.demo.creditos.domain.model.aggregates.Credito;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "resultado")
@NoArgsConstructor
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idResultado")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCredito", nullable = false)
    private Credito credito;

    // ============================
    // RESUMEN GENERAL
    // ============================

    @Column(name = "resumen_credito", columnDefinition = "text")
    private String resumenCredito;

    // ============================
    // INDICADORES FINANCIEROS
    // ============================

    @Column(name = "tir", precision = 14, scale = 8)
    private BigDecimal tir;

    @Column(name = "tcea", precision = 14, scale = 8)
    private BigDecimal tcea;

    @Column(name = "tea", precision = 14, scale = 8)
    private BigDecimal tea;

    @Column(name = "van", precision = 14, scale = 2)
    private BigDecimal van;

    // ============================
    // COSTOS / TOTALES
    // ============================

    @Column(name = "cuota_base", precision = 14, scale = 2)
    private BigDecimal cuotaBase;

    @Column(name = "total_intereses", precision = 14, scale = 2)
    private BigDecimal totalIntereses;

    @Column(name = "total_amortizacion", precision = 14, scale = 2)
    private BigDecimal totalAmortizacion;

    @Column(name = "total_seguro_desgravamen", precision = 14, scale = 2)
    private BigDecimal totalSeguroDesgravamen;

    @Column(name = "total_seguro_riesgo", precision = 14, scale = 2)
    private BigDecimal totalSeguroRiesgo;

    @Column(name = "total_comisiones", precision = 14, scale = 2)
    private BigDecimal totalComisiones;

    @Column(name = "total_portes_gastos", precision = 14, scale = 2)
    private BigDecimal totalPortesGastos;

    @Column(name = "total_pagado", precision = 14, scale = 2)
    private BigDecimal totalPagado;

    @Column(name = "costo_total_final", precision = 14, scale = 2)
    private BigDecimal costoTotalFinal;

    // ============================
    // RELACIÓN INGRESOS
    // ============================

    @Column(name = "porcentaje_ingreso", precision = 10, scale = 4)
    private BigDecimal porcentajeIngreso;

    // ============================
    // FLUJOS Y UTILIDAD
    // ============================

    @Column(name = "flujo_total", precision = 14, scale = 2)
    private BigDecimal flujoTotal;

    @Column(name = "saldo_final_flujo", precision = 14, scale = 2)
    private BigDecimal saldoFinalFlujo;

    // ============================
    // Constructor útil
    // ============================

    public Resultado(Credito credito) {
        this.credito = credito;
    }
}
