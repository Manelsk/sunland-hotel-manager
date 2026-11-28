package com.sunland.hotel.enums;

import com.sunland.hotel.exceptions.DadosInvalidosException;
import java.math.BigDecimal;

/**
 * Representa as categorias de suíte e seus multiplicadores sobre o preço base.
 */
public enum TipoSuite {
    EXECUTIVA("Executiva", new BigDecimal("1.50")),
    PRESIDENCIAL("Presidencial", new BigDecimal("2.00"));

    private final String descricao;
    private final BigDecimal multiplicador;

    TipoSuite(String descricao, BigDecimal multiplicador) {
        this.descricao = descricao;
        this.multiplicador = multiplicador;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getMultiplicador() {
        return multiplicador;
    }

    /**
     * Converte uma string para {@link TipoSuite} ignorando maiúsculas, minúsculas e espaços.
     *
     * @param valor texto informado pelo usuário
     * @return o enum correspondente
     * @throws DadosInvalidosException se o valor for nulo, vazio ou desconhecido
     */
    public static TipoSuite fromString(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new DadosInvalidosException("Tipo de suíte deve ser informado (Executiva ou Presidencial).");
        }
        String normalizado = valor.trim();
        for (TipoSuite tipo : values()) {
            if (tipo.name().equalsIgnoreCase(normalizado) || tipo.descricao.equalsIgnoreCase(normalizado)) {
                return tipo;
            }
        }
        throw new DadosInvalidosException("Tipo de suíte inválido: " + valor + ". Use Executiva ou Presidencial.");
    }
}
