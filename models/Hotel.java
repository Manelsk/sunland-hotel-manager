package models;

import java.util.ArrayList;

public class Hotel {
    private ArrayList<Quarto> quartos;
    private ArrayList<Hospede> hospedes;
    private ArrayList<Reserva> reservas;

    public void adicionarQuarto(Quarto quarto) {
        if(quarto == null){
            throw new IllegalArgumentException("Quarto deve ser informado.");
        }
        quartos.add(quarto);
    }

    public void adicionarHospede(Hospede hospede) {
        if(hospede == null){
            throw new IllegalArgumentException("Hóspede deve ser informado.");
        }
        hospedes.add(hospede);
    }

    public void criarReserva(Hospede hospede, Quarto quarto, int qtdeDiarias) {
        if(hospede == null){
            throw new IllegalArgumentException("Hóspede nao informado ou nao existe.");
        }
        if(quarto == null){
            throw new IllegalArgumentException("Quarto nao informado ou nao existe.");
        }
        Reserva reserva = new Reserva(hospede, quarto, qtdeDiarias);
        reservas.add(reserva);
    }

    
}

