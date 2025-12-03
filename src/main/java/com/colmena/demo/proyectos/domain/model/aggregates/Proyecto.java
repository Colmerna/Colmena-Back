package com.colmena.demo.proyectos.domain.model.aggregates;

import com.colmena.demo.proyectos.domain.model.valueobjects.EstadoProyecto;
import com.colmena.demo.proyectos.domain.model.valueobjects.TipoProyecto;
import com.colmena.demo.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "proyecto")
@NoArgsConstructor
public class Proyecto extends AuditableAbstractAggregateRoot<Proyecto> {

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(length = 80)
    private String distrito;

    @Column(name = "area_m2", nullable = false)
    private Double areaM2;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal igv; // 0.18 = 18%

    @Column(name = "num_habitaciones", nullable = false)
    private Integer numHabitaciones;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_proyecto", nullable = false)
    private TipoProyecto tipoProyecto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_proyecto", nullable = false)
    private EstadoProyecto estadoProyecto;

    @Column(name = "idBanco", nullable = false)
    private Long bancoId;

    public Proyecto(String nombre,
                    String direccion,
                    String distrito,
                    Double areaM2,
                    BigDecimal precio,
                    BigDecimal igv,
                    Integer numHabitaciones,
                    TipoProyecto tipoProyecto,
                    EstadoProyecto estadoProyecto,
                    Long bancoId) {

        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre del proyecto es obligatorio");
        if (direccion == null || direccion.isBlank())
            throw new IllegalArgumentException("La dirección es obligatoria");
        if (areaM2 == null || areaM2 <= 0)
            throw new IllegalArgumentException("El área debe ser mayor a 0");
        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        if (igv == null || igv.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("El IGV no puede ser negativo");
        if (numHabitaciones == null || numHabitaciones <= 0)
            throw new IllegalArgumentException("El número de habitaciones debe ser mayor a 0");
        if (tipoProyecto == null)
            throw new IllegalArgumentException("El tipo de proyecto es obligatorio");
        if (estadoProyecto == null)
            throw new IllegalArgumentException("El estado del proyecto es obligatorio");
        if (bancoId == null)
            throw new IllegalArgumentException("El banco es obligatorio");

        this.nombre = nombre;
        this.direccion = direccion;
        this.distrito = distrito;
        this.areaM2 = areaM2;
        this.precio = precio;
        this.igv = igv;
        this.numHabitaciones = numHabitaciones;
        this.tipoProyecto = tipoProyecto;
        this.estadoProyecto = estadoProyecto;
        this.bancoId = bancoId;
    }
}
