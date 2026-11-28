package com.sunland.hotel.enums;

/**
 * Representa o status físico atual de um quarto no hotel.
 */
public enum StatusQuarto {
    DISPONIVEL("Disponível"),
    OCUPADO("Ocupado"),
    MANUTENCAO("Em manutenção");

    private final String descricao;

    StatusQuarto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
