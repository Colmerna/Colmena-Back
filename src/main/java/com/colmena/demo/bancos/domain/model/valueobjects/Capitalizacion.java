package com.colmena.demo.bancos.domain.model.valueobjects;

public enum Capitalizacion {

    DIARIA(360),
    SEMANAL(52),
    QUINCENAL(24),
    MENSUAL(12),
    BIMESTRAL(6),
    TRIMESTRAL(4),
    SEMESTRAL(2),
    ANUAL(1);

    private final int periodsPerYear;

    Capitalizacion(int periodsPerYear) {
        this.periodsPerYear = periodsPerYear;
    }

    public int getPeriodsPerYear() {
        return periodsPerYear;
    }
}
