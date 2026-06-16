package models;

import java.util.ArrayList;

public class Hotel {
    private ArrayList<Quarto> quartos;
    private ArrayList<Hospede> hospedes;
    private ArrayList<Reserva> reservas;

    public Hotel() {
        this.quartos = new ArrayList<>();
        this.hospedes = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }
    
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
    
    public Quarto buscarQuartoporNumero(int numero) {
        for(Quarto quarto : quartos){
            if(quarto.getNumero() == numero){
                return quarto;
            }
        }
        throw new IllegalArgumentException("Quarto com numero " + numero + " nao encontrado.");
    }

    public Hospede buscarHospedePorCpf(String cpf) {
        for(Hospede hospede : hospedes){
            if(hospede.getCpf().equals(cpf)){
                return hospede;
            }
        }
        throw new IllegalArgumentException("Hóspede com CPF " + cpf + " nao encontrado.");
    }

    public Reserva buscarReservaPorCpf(String cpf) {
        for(Reserva reserva : reservas){
            if(reserva.getHospede().getCpf().equals(cpf)){
                return reserva;
            }
        }
        throw new IllegalArgumentException("Reserva para hóspede com CPF " + cpf + " nao encontrada.");
    }
    
    public ArrayList<Hospede> getHospedes() {
        return hospedes;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public ArrayList<Quarto> getQuartos() {
        return quartos;
    }   
}

