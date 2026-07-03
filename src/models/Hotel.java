package models;

import exceptions.DadosInexistentesException;
import exceptions.DadosNaoInformadosException;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um hotel com gerenciamento de quartos, hóspedes e reservas.
 * 
 * A classe Hotel encapsula a lógica de negócio para gerenciar quartos disponíveis,
 * registrar hóspedes e criar/gerenciar reservas, garantindo a integridade dos dados
 * através de validações e verificações de existência.
 */
public class Hotel {
    /** Lista de quartos do hotel */
    private List<Quarto> quartos;
    
    /** Lista de hóspedes registrados no hotel */
    private List<Hospede> hospedes;
    
    /** Lista de reservas realizadas no hotel */
    private List<Reserva> reservas;

    public Hotel() {
        this.quartos = new ArrayList<>();
        this.hospedes = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }
    
    /**
     * Adiciona um novo quarto ao hotel.
     * 
     * @param quarto o quarto a ser adicionado
     * @throws DadosNaoInformadosException se o quarto for null
     * @throws IllegalArgumentException se já existe um quarto com o mesmo número
     */
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

    /**
     * Adiciona um novo hóspede ao hotel.
     * 
     * @param hospede o hóspede a ser adicionado
     * @throws DadosNaoInformadosException se o hóspede for null
     */
    public void adicionarHospede(Hospede hospede) throws DadosNaoInformadosException {
        if(hospede == null){
            throw new DadosNaoInformadosException("Hóspede deve ser informado.");
        }
        hospedes.add(hospede);
    }

    /**
     * Cria uma nova reserva para um hóspede em um quarto.
     * 
     * @param hospede o hóspede que está realizando a reserva
     * @param quarto o quarto a ser reservado
     * @param qtdeDiarias a quantidade de diárias da reserva
     * @throws DadosInexistentesException se o hóspede ou quarto não existem no hotel
     */
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
    
    /**
     * Busca um quarto pelo seu número.
     * 
     * @param numero o número do quarto a ser buscado
     * @return o quarto encontrado
     * @throws DadosInexistentesException se nenhum quarto com esse número foi encontrado
     */
    public Quarto buscarQuartoporNumero(int numero) throws DadosInexistentesException {
        for(Quarto quarto : quartos){
            if(quarto.getNumero() == numero){
                return quarto;
            }
        }
        throw new DadosInexistentesException("Quarto com numero " + numero + " nao encontrado.");
    }

    /**
     * Busca um hóspede pelo seu CPF.
     * 
     * @param cpf o CPF do hóspede a ser buscado
     * @return o hóspede encontrado
     * @throws DadosInexistentesException se nenhum hóspede com esse CPF foi encontrado
     */
    public Hospede buscarHospedePorCpf(String cpf) throws DadosInexistentesException {
        for(Hospede hospede : hospedes){
            if(hospede.getCpf().equals(cpf)){
                return hospede;
            }
        }
        throw new DadosInexistentesException("Hóspede com CPF " + cpf + " nao encontrado.");
    }

    /**
     * Busca uma reserva pelo CPF do hóspede associado.
     * 
     * @param cpf o CPF do hóspede cuja reserva será buscada
     * @return a reserva encontrada
     * @throws DadosInexistentesException se nenhuma reserva para esse CPF foi encontrada
     */
    public Reserva buscarReservaPorCpf(String cpf) throws DadosInexistentesException {
        for(Reserva reserva : reservas){
            if(reserva.getHospede().getCpf().equals(cpf)){
                return reserva;
            }
        }
        throw new DadosInexistentesException("Reserva para hóspede com CPF " + cpf + " nao encontrada.");
    }

    /**
     * Exclui um hóspede do hotel.
     * 
     * @param hospede o hóspede a ser excluído
     * @throws DadosInexistentesException se o hóspede não existe no hotel
     * @throws IllegalStateException se o hóspede possui reservas ativas
     */
    public void excluirHospede(Hospede hospede) throws DadosInexistentesException {
        if(hospedes.contains(hospede) == false){
            throw new DadosInexistentesException("Hóspede informado nao existe.");
        }
        for (Reserva reserva : reservas) {
        if (reserva.getHospede().equals(hospede)) {
            throw new IllegalStateException("Hóspede informado não pode ser excluído pois possui reservas.");
            }
        }
        hospedes.remove(hospede);
    }
    
    /**
     * Exclui uma reserva do hotel.
     * 
     * @param reserva a reserva a ser excluída
     * @throws DadosInexistentesException se a reserva não existe no hotel
     * @throws IllegalStateException se a reserva já foi confirmada
     */
    public void excluirReserva(Reserva reserva) throws DadosInexistentesException {
        if(reservas.contains(reserva) == false){
            throw new DadosInexistentesException("Reserva informada nao existe.");
        }else if("Confirmada".equals(reserva.getEstadoReserva())){
            throw new IllegalStateException("Reserva informada nao pode ser excluida pois ja foi confirmada.");
        }
        
        reservas.remove(reserva);
    }

    /**
     * Exclui um quarto do hotel.
     * 
     * @param quarto o quarto a ser excluído
     * @throws DadosInexistentesException se o quarto não existe no hotel
     * @throws IllegalStateException se o quarto está reservado
     */
    public void excluirQuarto(Quarto quarto) throws DadosInexistentesException {
        if(quartos.contains(quarto) == false){
            throw new DadosInexistentesException("Quarto informado nao existe.");
        }else if(quarto.estaDisponivel() == false){
            throw new IllegalStateException("Quarto informado nao pode ser excluido pois esta reservado.");
        }
        
        quartos.remove(quarto);
    }

    /**
     * Obtém a lista de hóspedes do hotel.
     * 
     * @return a lista de hóspedes
     */
    public List<Hospede> getHospedes() {
        return hospedes;
    }

    /**
     * Obtém a lista de reservas do hotel.
     * 
     * @return a lista de reservas
     */
    public List<Reserva> getReservas() {
        return reservas;
    }

    /**
     * Obtém a lista de quartos do hotel.
     * 
     * @return a lista de quartos
     */
    public List<Quarto> getQuartos() {
        return quartos;
    }   
}

