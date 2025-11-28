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

    @Column(name = "resumen_credito", columnDefinition = "text")
    private String resumenCredito;

    @Column(name = "tcea", precision = 14, scale = 8)
    private BigDecimal tcea;

    @Column(name = "tea", precision = 14, scale = 8)
    private BigDecimal tea;

    @Column(name = "cuota_base", precision = 14, scale = 2)
    private BigDecimal cuotaBase;

    @Column(name = "total_intereses", precision = 14, scale = 2)
    private BigDecimal totalIntereses;

    @Column(name = "total_pagado", precision = 14, scale = 2)
    private BigDecimal totalPagado;

    @Column(name = "costo_total_final", precision = 14, scale = 2)
    private BigDecimal costoTotalFinal;

    @Column(name = "porcentaje_ingreso", precision = 6, scale = 4)
    private BigDecimal porcentajeIngreso;

    @Column(name = "tir_cliente", precision = 14, scale = 8)
    private BigDecimal tirCliente;

    @Column(name = "van_cliente", precision = 14, scale = 2)
    private BigDecimal vanCliente;

    public Resultado(Credito credito) {
        this.credito = credito;
    }
}
