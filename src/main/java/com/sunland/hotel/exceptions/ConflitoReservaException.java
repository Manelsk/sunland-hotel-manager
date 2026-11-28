package com.sunland.hotel.exceptions;

/**
 * Exceção lançada quando ocorre conflito de unicidade ou indisponibilidade de agenda (overbooking).
 */
public class ConflitoReservaException extends RuntimeException {
    public ConflitoReservaException(String message) {
        super(message);
    }

    public ConflitoReservaException() {
        super();
    }
}
