package com.sunland.hotel.models;

import com.sunland.hotel.enums.TipoSuite;
import com.sunland.hotel.exceptions.DadosNaoInformadosException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Representa uma suíte do hotel (Executiva 1.5x ou Presidencial 2.0x).
 */
public class Suite extends Quarto {

    private TipoSuite tipoSuite;

    public Suite(int numero, BigDecimal precoBase, int capacidade, TipoSuite tipoSuite) {
        super(numero, precoBase, capacidade);
        setTipoSuite(tipoSuite);
    }

    public TipoSuite getTipoSuite() {
        return tipoSuite;
    }

    public void setTipoSuite(TipoSuite tipoSuite) {
        if (tipoSuite == null) {
            throw new DadosNaoInformadosException("Tipo de suíte deve ser informado.");
        }
        this.tipoSuite = tipoSuite;
    }

    @Override
    public BigDecimal calcularDiaria() {
        return getPrecoBase().multiply(tipoSuite.getMultiplicador()).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String exibirDescricao() {
        return "======== Suíte " + tipoSuite.getDescricao() + " ========\n"
                + "Número: " + getNumero() + "\n"
                + "Capacidade: " + getCapacidade() + " pessoas\n"
                + "Preço da diária: R$ " + calcularDiaria() + "\n"
                + "Status: " + getStatus().getDescricao() + "\n";
    }
}
