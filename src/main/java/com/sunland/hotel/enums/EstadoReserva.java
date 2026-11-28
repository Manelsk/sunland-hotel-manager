package com.sunland.hotel.enums;

/**
 * Representa o ciclo de vida de uma reserva no hotel.
 */
public enum EstadoReserva {
    PENDENTE("Pendente"),
    CONFIRMADA("Confirmada"),
    ATIVA("Ativa"),
    FINALIZADA("Finalizada"),
    CANCELADA("Cancelada");

    private final String descricao;

    EstadoReserva(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
