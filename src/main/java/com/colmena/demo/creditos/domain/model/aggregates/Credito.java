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

    @Column(name = "idCliente", nullable = false)
    private Long clienteId;

    @Column(name = "idProyecto", nullable = false)
    private Long proyectoId;

    @Column(name = "idBanco", nullable = false)
    private Long bancoId;

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

    // capital del préstamo (PV - cuotaInicial - bonoTecho)
    @Column(name = "monto_prestamo", precision = 14, scale = 2, nullable = false)
    private BigDecimal montoPrestamo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_credito", nullable = false)
    private EstadoCredito estadoCredito;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_aprobacion")
    private LocalDateTime fechaAprobacion;

    public Credito(Long clienteId,
                   Long proyectoId,
                   Long bancoId,
                   Moneda moneda,
                   TipoTasaInteres tipoTasa,
                   BigDecimal tasaNominal,
                   BigDecimal tasaEfectiva,
                   BaseTiempo baseTiempo,
                   Capitalizacion capitalizacion,
                   BigDecimal cuotaInicial,
                   BigDecimal bonoTecho,
                   GraciaTipo graciaTipo,
                   Integer graciaMeses,
                   Integer plazoMeses,
                   BigDecimal montoPrestamo,
                   EstadoCredito estadoCredito) {

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
        this.cuotaInicial = cuotaInicial;
        this.bonoTecho = bonoTecho;
        this.graciaTipo = graciaTipo;
        this.graciaMeses = graciaMeses;
        this.plazoMeses = plazoMeses;
        this.montoPrestamo = montoPrestamo;
        this.estadoCredito = estadoCredito;
        this.fechaCreacion = LocalDateTime.now();
    }
}
