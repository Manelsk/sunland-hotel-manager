package com.sunland.hotel.models;

import com.sunland.hotel.enums.TipoCama;
import com.sunland.hotel.exceptions.DadosNaoInformadosException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Representa um quarto duplo do hotel, com tipo de cama e opção de varanda (+20%).
 */
public class QuartoDuplo extends Quarto {

    private static final BigDecimal FATOR_VARANDA = new BigDecimal("1.20");
    private TipoCama tipoCama;
    private boolean possuiVaranda;

    public QuartoDuplo(int numero, BigDecimal precoBase, int capacidade, TipoCama tipoCama, boolean possuiVaranda) {
        super(numero, precoBase, capacidade);
        setTipoCama(tipoCama);
        this.possuiVaranda = possuiVaranda;
    }

    public TipoCama getTipoCama() {
        return tipoCama;
    }

    public void setTipoCama(TipoCama tipoCama) {
        if (tipoCama == null) {
            throw new DadosNaoInformadosException("Tipo de cama deve ser informado.");
        }
        this.tipoCama = tipoCama;
    }

    public boolean isPossuiVaranda() {
        return possuiVaranda;
    }

    public void setPossuiVaranda(boolean possuiVaranda) {
        this.possuiVaranda = possuiVaranda;
    }

    @Override
    public BigDecimal calcularDiaria() {
        if (possuiVaranda) {
            return getPrecoBase().multiply(FATOR_VARANDA).setScale(2, RoundingMode.HALF_UP);
        }
        return getPrecoBase();
    }

    @Override
    public String exibirDescricao() {
        return "======== Quarto Duplo (" + tipoCama.getDescricao() + ") ========\n"
                + "Número: " + getNumero() + "\n"
                + "Capacidade: " + getCapacidade() + " pessoas\n"
                + "Varanda: " + (possuiVaranda ? "Sim" : "Não") + "\n"
                + "Preço da diária: R$ " + calcularDiaria() + "\n"
                + "Status: " + getStatus().getDescricao() + "\n";
    }
}
