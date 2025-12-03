package com.colmena.demo.bancos.domain.model.aggregates;

import com.colmena.demo.bancos.domain.model.valueobjects.Capitalizacion;
import com.colmena.demo.bancos.domain.model.valueobjects.EstadoBanco;
import com.colmena.demo.bancos.domain.model.valueobjects.Moneda;
import com.colmena.demo.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "bancos")
@NoArgsConstructor
public class Banco extends AuditableAbstractAggregateRoot<Banco> {

    @Column(nullable = false, unique = true, length = 80)
    private String nombre;

    @Column(length = 20)
    private String ruc;

    @Column(length = 20)
    private String telefono;

    @Column(name = "sitio_web", length = 120)
    private String sitioWeb;

    @Enumerated(EnumType.STRING)
    @Column(name = "moneda_base", nullable = false)
    private Moneda monedaBase; // PEN o USD

    @Column(name = "usa_tasas_nominales", nullable = false)
    private boolean usaTasasNominales;

    @Enumerated(EnumType.STRING)
    @Column(name = "capitalizacion_default", nullable = false)
    private Capitalizacion capitalizacionDefault;

    @Column(name = "tipo_cambio_compra", precision = 10, scale = 4)
    private BigDecimal tipoCambioCompra;

    @Column(name = "tipo_cambio_venta", precision = 10, scale = 4)
    private BigDecimal tipoCambioVenta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoBanco estado;

    public Banco(String nombre,
                 String ruc,
                 String telefono,
                 String sitioWeb,
                 Moneda monedaBase,
                 boolean usaTasasNominales,
                 Capitalizacion capitalizacionDefault,
                 BigDecimal tipoCambioCompra,
                 BigDecimal tipoCambioVenta,
                 EstadoBanco estado) {

        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre del banco no puede ser vacío");
        if (monedaBase == null)
            throw new IllegalArgumentException("La moneda base es obligatoria");
        if (capitalizacionDefault == null)
            throw new IllegalArgumentException("La capitalización por defecto es obligatoria");
        if (estado == null)
            throw new IllegalArgumentException("El estado del banco es obligatorio");

        this.nombre = nombre;
        this.ruc = ruc;
        this.telefono = telefono;
        this.sitioWeb = sitioWeb;
        this.monedaBase = monedaBase;
        this.usaTasasNominales = usaTasasNominales;
        this.capitalizacionDefault = capitalizacionDefault;
        this.tipoCambioCompra = tipoCambioCompra;
        this.tipoCambioVenta = tipoCambioVenta;
        this.estado = estado;
    }

    // Método de negocio para actualizar tipo de cambio
    public void actualizarTipoCambio(BigDecimal compra, BigDecimal venta) {
        if (compra != null && compra.compareTo(BigDecimal.ZERO) > 0) {
            this.tipoCambioCompra = compra;
        }
        if (venta != null && venta.compareTo(BigDecimal.ZERO) > 0) {
            this.tipoCambioVenta = venta;
        }
    }
}
