package com.sunland.hotel.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Representa um quarto simples do hotel, com acréscimo de 20% se possuir ar-condicionado.
 */
public class QuartoSimples extends Quarto {

    private static final BigDecimal FATOR_AR_CONDICIONADO = new BigDecimal("1.20");
    private boolean possuiArCondicionado;

    public QuartoSimples(int numero, BigDecimal precoBase, int capacidade, boolean possuiArCondicionado) {
        super(numero, precoBase, capacidade);
        this.possuiArCondicionado = possuiArCondicionado;
    }

    public boolean isPossuiArCondicionado() {
        return possuiArCondicionado;
    }

    public void setPossuiArCondicionado(boolean possuiArCondicionado) {
        this.possuiArCondicionado = possuiArCondicionado;
    }

    @Override
    public BigDecimal calcularDiaria() {
        if (possuiArCondicionado) {
            return getPrecoBase().multiply(FATOR_AR_CONDICIONADO).setScale(2, RoundingMode.HALF_UP);
        }
        return getPrecoBase();
    }

    @Override
    public String exibirDescricao() {
        return "======== Quarto Simples ========\n"
                + "Número: " + getNumero() + "\n"
                + "Capacidade: " + getCapacidade() + " pessoas\n"
                + "Ar-condicionado: " + (possuiArCondicionado ? "Sim" : "Não") + "\n"
                + "Preço da diária: R$ " + calcularDiaria() + "\n"
                + "Status: " + getStatus().getDescricao() + "\n";
    }
}
