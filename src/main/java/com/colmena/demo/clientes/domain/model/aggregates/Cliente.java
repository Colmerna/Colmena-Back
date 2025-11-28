package com.colmena.demo.clientes.domain.model.aggregates;

import com.colmena.demo.clientes.domain.model.valueobjects.EstadoCivil;
import com.colmena.demo.clientes.domain.model.valueobjects.SituacionLaboral;
import com.colmena.demo.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Entity
@Setter
@Table(name = "cliente")
@NoArgsConstructor
public class Cliente extends AuditableAbstractAggregateRoot<Cliente> {

    @Column(nullable = false, length = 12)
    private String dni;

    @Column(nullable = false, length = 80)
    private String nombres;

    @Column(nullable = false, length = 80)
    private String apellidos;

    @Column(length = 20)
    private String telefono;

    @Column(length = 120)
    private String email;

    @Column(name = "ingreso_mensual", precision = 12, scale = 2, nullable = false)
    private BigDecimal ingresoMensual;

    @Column(nullable = false)
    private Integer dependientes;

    @Column(name = "score_riesgo", precision = 5, scale = 2)
    private BigDecimal scoreRiesgo;

    @Column(name = "gasto_mensual_aprox", precision = 12, scale = 2)
    private BigDecimal gastoMensualAprox;

    @Column(name = "idUsuario")
    private Long usuarioId;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacion_laboral", nullable = false)
    private SituacionLaboral situacionLaboral;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_civil", nullable = false)
    private EstadoCivil estadoCivil;

    public Cliente(String dni,
                   String nombres,
                   String apellidos,
                   String telefono,
                   String email,
                   BigDecimal ingresoMensual,
                   Integer dependientes,
                   BigDecimal scoreRiesgo,
                   BigDecimal gastoMensualAprox,
                   Long usuarioId,
                   SituacionLaboral situacionLaboral,
                   EstadoCivil estadoCivil) {

        if (dni == null || dni.isBlank()) throw new IllegalArgumentException("El DNI no puede ser vacío");
        if (nombres == null || nombres.isBlank()) throw new IllegalArgumentException("Los nombres no pueden ser vacíos");
        if (apellidos == null || apellidos.isBlank()) throw new IllegalArgumentException("Los apellidos no pueden ser vacíos");
        if (ingresoMensual == null || ingresoMensual.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("El ingreso mensual debe ser mayor a 0");
        if (dependientes == null || dependientes < 0)
            throw new IllegalArgumentException("Los dependientes no pueden ser negativos");
        if (situacionLaboral == null)
            throw new IllegalArgumentException("La situación laboral es obligatoria");
        if (estadoCivil == null)
            throw new IllegalArgumentException("El estado civil es obligatorio");

        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.ingresoMensual = ingresoMensual;
        this.dependientes = dependientes;
        this.scoreRiesgo = scoreRiesgo;
        this.gastoMensualAprox = gastoMensualAprox;
        this.usuarioId = usuarioId;
        this.situacionLaboral = situacionLaboral;
        this.estadoCivil = estadoCivil;
    }
}
