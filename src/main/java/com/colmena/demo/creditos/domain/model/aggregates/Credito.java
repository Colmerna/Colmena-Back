package com.colmena.demo.creditos.domain.model.aggregates;

import com.colmena.demo.bancos.domain.model.valueobjects.Capitalizacion;
import com.colmena.demo.bancos.domain.model.valueobjects.Moneda;
import com.colmena.demo.creditos.domain.model.valueobjects.BaseTiempo;
import com.colmena.demo.creditos.domain.model.valueobjects.EstadoCredito;
import com.colmena.demo.creditos.domain.model.valueobjects.GraciaTipo;
import com.colmena.demo.creditos.domain.model.valueobjects.TipoTasaInteres;
import com.colmena.demo.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "credito")
@NoArgsConstructor
public class Credito extends AuditableAbstractAggregateRoot<Credito> {

    // ----------------- Relaciones básicas -----------------

    @Column(name = "idCliente", nullable = false)
    private Long clienteId;

    @Column(name = "idProyecto", nullable = false)
    private Long proyectoId;

    @Column(name = "idBanco", nullable = false)
    private Long bancoId;

    // ----------------- Datos financieros base -----------------

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Moneda moneda;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_tasa", nullable = false)
    private TipoTasaInteres tipoTasa;

    @Column(name = "tasa_nominal", precision = 8, scale = 4)
    private BigDecimal tasaNominal;

    @Column(name = "tasa_efectiva", precision = 8, scale = 4)
    private BigDecimal tasaEfectiva;

    @Enumerated(EnumType.STRING)
    @Column(name = "base_tiempo", nullable = false)
    private BaseTiempo baseTiempo;

    @Enumerated(EnumType.STRING)
    @Column(name = "capitalizacion", nullable = false)
    private Capitalizacion capitalizacion;

    // Precio de venta del activo (para reflejar el Excel)
    @Column(name = "precio_venta_activo", precision = 14, scale = 2)
    private BigDecimal precioVentaActivo;

    // ----------------- Aportes / condiciones del crédito -----------------

    @Column(name = "cuota_inicial", precision = 14, scale = 2, nullable = false)
    private BigDecimal cuotaInicial;

    @Column(name = "bono_techo", precision = 14, scale = 2)
    private BigDecimal bonoTecho;

    @Enumerated(EnumType.STRING)
    @Column(name = "gracia_tipo", nullable = false)
    private GraciaTipo graciaTipo;

    @Column(name = "gracia_meses")
    private Integer graciaMeses;

    @Column(name = "plazo_meses", nullable = false)
    private Integer plazoMeses;

    // capital del préstamo (Saldo a financiar + costos iniciales)
    @Column(name = "monto_prestamo", precision = 14, scale = 2, nullable = false)
    private BigDecimal montoPrestamo;

    // ----------------- Costos / gastos iniciales (Excel) -----------------

    @Column(name = "costos_notariales", precision = 14, scale = 2)
    private BigDecimal costosNotariales;

    @Column(name = "costos_registrales", precision = 14, scale = 2)
    private BigDecimal costosRegistrales;

    @Column(name = "tasacion", precision = 14, scale = 2)
    private BigDecimal tasacion;

    @Column(name = "comision_estudio", precision = 14, scale = 2)
    private BigDecimal comisionEstudio;

    @Column(name = "comision_activacion", precision = 14, scale = 2)
    private BigDecimal comisionActivacion;

    // ----------------- Costos / gastos periódicos (config) -----------------

    @Column(name = "comision_periodica", precision = 14, scale = 2)
    private BigDecimal comisionPeriodica;

    @Column(name = "portes", precision = 14, scale = 2)
    private BigDecimal portes;

    @Column(name = "gastos_adm_periodicos", precision = 14, scale = 2)
    private BigDecimal gastosAdmPeriodicos;

    @Column(name = "porc_seguro_desgravamen", precision = 8, scale = 4)
    private BigDecimal porcentajeSeguroDesgravamen;

    @Column(name = "porc_seguro_riesgo", precision = 8, scale = 4)
    private BigDecimal porcentajeSeguroRiesgo;

    // ----------------- Costo de oportunidad -----------------

    @Column(name = "tasa_descuento_anual", precision = 8, scale = 4)
    private BigDecimal tasaDescuentoAnual;

    // ----------------- Configuración de frecuencia (MiVivienda) -----------------

    @Column(name = "frecuencia_pago_dias", nullable = false)
    private Integer frecuenciaPagoDias = 30;   // 30 días entre cuotas (mensual)

    @Column(name = "dias_por_anio", nullable = false)
    private Integer diasPorAnio = 360;         // Año comercial 360 días

    // ----------------- Estado y auditoría -----------------

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_credito", nullable = false)
    private EstadoCredito estadoCredito;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_aprobacion")
    private LocalDateTime fechaAprobacion;


    // ----------------- Constructor de dominio -----------------

    public Credito(Long clienteId,
                   Long proyectoId,
                   Long bancoId,
                   Moneda moneda,
                   TipoTasaInteres tipoTasa,
                   BigDecimal tasaNominal,
                   BigDecimal tasaEfectiva,
                   BaseTiempo baseTiempo,
                   Capitalizacion capitalizacion,
                   BigDecimal precioVentaActivo,
                   BigDecimal cuotaInicial,
                   BigDecimal bonoTecho,
                   GraciaTipo graciaTipo,
                   Integer graciaMeses,
                   Integer plazoMeses,
                   BigDecimal montoPrestamo,
                   // costos iniciales
                   BigDecimal costosNotariales,
                   BigDecimal costosRegistrales,
                   BigDecimal tasacion,
                   BigDecimal comisionEstudio,
                   BigDecimal comisionActivacion,
                   // costos periódicos
                   BigDecimal comisionPeriodica,
                   BigDecimal portes,
                   BigDecimal gastosAdmPeriodicos,
                   BigDecimal porcentajeSeguroDesgravamen,
                   BigDecimal porcentajeSeguroRiesgo,
                   // tasa de descuento
                   BigDecimal tasaDescuentoAnual,
                   // frecuencia / días por año
                   Integer frecuenciaPagoDias,
                   Integer diasPorAnio,
                   EstadoCredito estadoCredito) {

        // Validaciones básicas
        if (clienteId == null) throw new IllegalArgumentException("El cliente es obligatorio");
        if (proyectoId == null) throw new IllegalArgumentException("El proyecto es obligatorio");
        if (bancoId == null) throw new IllegalArgumentException("El banco es obligatorio");
        if (moneda == null) throw new IllegalArgumentException("La moneda es obligatoria");
        if (tipoTasa == null) throw new IllegalArgumentException("El tipo de tasa es obligatorio");
        if (baseTiempo == null) throw new IllegalArgumentException("La base de tiempo es obligatoria");
        if (capitalizacion == null) throw new IllegalArgumentException("La capitalización es obligatoria");
        if (cuotaInicial == null || cuotaInicial.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("La cuota inicial no puede ser negativa");
        if (plazoMeses == null || plazoMeses <= 0)
            throw new IllegalArgumentException("El plazo debe ser mayor a 0");
        if (montoPrestamo == null || montoPrestamo.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("El monto del préstamo debe ser mayor a 0");
        if (estadoCredito == null)
            throw new IllegalArgumentException("El estado del crédito es obligatorio");

        this.clienteId = clienteId;
        this.proyectoId = proyectoId;
        this.bancoId = bancoId;
        this.moneda = moneda;
        this.tipoTasa = tipoTasa;
        this.tasaNominal = tasaNominal;
        this.tasaEfectiva = tasaEfectiva;
        this.baseTiempo = baseTiempo;
        this.capitalizacion = capitalizacion;
        this.precioVentaActivo = precioVentaActivo;
        this.cuotaInicial = cuotaInicial;
        this.bonoTecho = bonoTecho;
        this.graciaTipo = graciaTipo;
        this.graciaMeses = graciaMeses;
        this.plazoMeses = plazoMeses;
        this.montoPrestamo = montoPrestamo;

        this.costosNotariales = costosNotariales;
        this.costosRegistrales = costosRegistrales;
        this.tasacion = tasacion;
        this.comisionEstudio = comisionEstudio;
        this.comisionActivacion = comisionActivacion;

        this.comisionPeriodica = comisionPeriodica;
        this.portes = portes;
        this.gastosAdmPeriodicos = gastosAdmPeriodicos;
        this.porcentajeSeguroDesgravamen = porcentajeSeguroDesgravamen;
        this.porcentajeSeguroRiesgo = porcentajeSeguroRiesgo;

        this.tasaDescuentoAnual = tasaDescuentoAnual;

        // Frecuencia y días por año (defaults MiVivienda si vienen null o inválidos)
        if (frecuenciaPagoDias == null || frecuenciaPagoDias <= 0) {
            this.frecuenciaPagoDias = 30;
        } else {
            this.frecuenciaPagoDias = frecuenciaPagoDias;
        }

        if (diasPorAnio == null || diasPorAnio <= 0) {
            this.diasPorAnio = 360;
        } else {
            this.diasPorAnio = diasPorAnio;
        }

        this.estadoCredito = estadoCredito;
        this.fechaCreacion = LocalDateTime.now();
    }

    // ----------------- Helpers para costos -----------------

    public BigDecimal getTotalCostosIniciales() {
        return zeroIfNull(costosNotariales)
                .add(zeroIfNull(costosRegistrales))
                .add(zeroIfNull(tasacion))
                .add(zeroIfNull(comisionEstudio))
                .add(zeroIfNull(comisionActivacion));
    }

    private BigDecimal zeroIfNull(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}
