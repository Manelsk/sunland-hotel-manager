package com.sunland.hotel.interfaces;

import java.math.BigDecimal;

/**
 * Contrato para recursos do hotel que podem ser ocupados, liberados e precificados.
 */
public interface Reservavel {

    /**
     * Marca o recurso como ocupado durante uma estadia ativa.
     */
    void ocupar();

    /**
     * Libera o recurso após término da estadia.
     */
    void liberar();

    /**
     * Verifica se o recurso está disponível (não ocupado e não em manutenção).
     *
     * @return {@code true} se disponível; {@code false} caso contrário
     */
    boolean estaDisponivel();

    /**
     * Calcula o valor da diária do recurso com precisão monetária.
     *
     * @return valor da diária
     */
    BigDecimal calcularDiaria();
}
