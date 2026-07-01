package models;

import exceptions.DadosInexistentesException;
import exceptions.DadosNaoInformadosException;
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
    
    public void adicionarQuarto(Quarto quarto) throws DadosNaoInformadosException, IllegalArgumentException {
        if(quarto == null){
            throw new DadosNaoInformadosException("Quarto deve ser informado.");
        }
        
        for(Quarto q : quartos){
            if(q.getNumero() == quarto.getNumero()){
                throw new IllegalArgumentException("Quarto com numero " + quarto.getNumero() + " já existe, por favor escolha outro numero.");
            }
        }
            quartos.add(quarto);
    }

    public void adicionarHospede(Hospede hospede) throws DadosNaoInformadosException {
        if(hospede == null){
            throw new DadosNaoInformadosException("Hóspede deve ser informado.");
        }
        hospedes.add(hospede);
    }

    public void criarReserva(Hospede hospede, Quarto quarto, int qtdeDiarias) throws DadosInexistentesException {
        if(hospedes.contains(hospede) == false){
            throw new DadosInexistentesException("Hóspede informado nao existe.");
        }
        if(quartos.contains(quarto) == false){
            throw new DadosInexistentesException("Quarto informado nao existe.");
        }
        Reserva reserva = new Reserva(hospede, quarto, qtdeDiarias);
        reservas.add(reserva);
    }
    
    public Quarto buscarQuartoporNumero(int numero) throws DadosInexistentesException {
        for(Quarto quarto : quartos){
            if(quarto.getNumero() == numero){
                return quarto;
            }
        }
        throw new DadosInexistentesException("Quarto com numero " + numero + " nao encontrado.");
    }

    public Hospede buscarHospedePorCpf(String cpf) throws DadosInexistentesException {
        for(Hospede hospede : hospedes){
            if(hospede.getCpf().equals(cpf)){
                return hospede;
            }
        }
        throw new DadosInexistentesException("Hóspede com CPF " + cpf + " nao encontrado.");
    }

    public Reserva buscarReservaPorCpf(String cpf) throws DadosInexistentesException {
        for(Reserva reserva : reservas){
            if(reserva.getHospede().getCpf().equals(cpf)){
                return reserva;
            }
        }
        throw new DadosInexistentesException("Reserva para hóspede com CPF " + cpf + " nao encontrada.");
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

