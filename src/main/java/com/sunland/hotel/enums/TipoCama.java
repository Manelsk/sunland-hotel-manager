package com.sunland.hotel.enums;

import com.sunland.hotel.exceptions.DadosInvalidosException;

/**
 * Representa os tipos de cama disponíveis em um quarto duplo.
 */
public enum TipoCama {
    CASAL("Casal"),
    SOLTEIRO("Solteiro");

    private final String descricao;

    TipoCama(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Converte uma string para {@link TipoCama} ignorando maiúsculas, minúsculas e espaços.
     *
     * @param valor texto informado pelo usuário
     * @return o enum correspondente
     * @throws DadosInvalidosException se o valor for nulo, vazio ou desconhecido
     */
    public static TipoCama fromString(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new DadosInvalidosException("Tipo de cama deve ser informado (Casal ou Solteiro).");
        }
        String normalizado = valor.trim();
        for (TipoCama tipo : values()) {
            if (tipo.name().equalsIgnoreCase(normalizado) || tipo.descricao.equalsIgnoreCase(normalizado)) {
                return tipo;
            }
        }
        throw new DadosInvalidosException("Tipo de cama inválido: " + valor + ". Use Casal ou Solteiro.");
    }
}
