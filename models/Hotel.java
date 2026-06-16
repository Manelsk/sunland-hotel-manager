package models;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private List<Quarto> quartos;
    private List<Hospede> hospedes;
    private List<Reserva> reservas;

    public Hotel() {
        this.quartos = new ArrayList<>();
        this.hospedes = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }
    
    public void adicionarQuarto(Quarto quarto) {
        if(quarto == null){
            throw new IllegalArgumentException("Quarto deve ser informado.");
        }
        
        for(Quarto q : quartos){
            if(q.getNumero() == quarto.getNumero()){
                throw new IllegalArgumentException("Quarto com numero " + quarto.getNumero() + " já existe, por favor escolha outro numero.");
            }
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
        if(hospedes.contains(hospede) == false){
            throw new IllegalArgumentException("Hóspede informado nao existe.");
        }
        if(quartos.contains(quarto) == false){
            throw new IllegalArgumentException("Quarto informado nao existe.");
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
    
    public List<Hospede> getHospedes() {
        return hospedes;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public List<Quarto> getQuartos() {
        return quartos;
    }   
}

