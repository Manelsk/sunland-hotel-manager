package models;

/**
 * Representa uma reserva de quarto no hotel.
 * 
 * A classe Reserva encapsula todas as informações e operações relacionadas
 * a uma reserva, incluindo hóspede, quarto, período de diárias e controle de
 * ciclo de vida da reserva (Pendente → Confirmada → Ativa → Finalizada).
 * 
 * @author Sistema Sunland Hotel Manager
 * @version 1.0
 * @since 1.0
 */
public class Reserva{
    /** Hóspede que realizou a reserva */
    private Hospede hospede;
    
    /** Quarto reservado */
    private Quarto quarto;
    
    /** Quantidade de diárias da reserva */
    private int qtdeDiarias;
    
    /** Estado da reserva: Pendente, Confirmada, Ativa ou Finalizada */
    private String estadoReserva;

    /**
     * Constrói uma nova Reserva com os dados do hóspede, quarto e diárias.
     * 
     * O estado inicial da reserva é "Pendente".
     * 
     * @param hospede o hóspede que está realizando a reserva
     * @param quarto o quarto a ser reservado
     * @param qtdeDiarias a quantidade de diárias
     * @throws IllegalArgumentException se o hóspede ou quarto forem null,
     *         se o quarto não estiver disponível, ou se a quantidade de
     *         diárias for menor ou igual a zero
     */
    public Reserva(Hospede hospede, Quarto quarto, int qtdeDiarias) {
        setHospede(hospede);
        setQuarto(quarto);
        setQtdeDiarias(qtdeDiarias);
        this.estadoReserva = "Pendente";
    }

    /**
     * Confirma uma reserva pendente.
     * 
     * Muda o estado de "Pendente" para "Confirmada" e marca o quarto como reservado.
     * 
     * @throws IllegalStateException se a reserva já foi confirmada ou se não está
     *         no estado "Pendente"
     */
    public void confirmaReserva() {
        if("Pendente".equals(estadoReserva)){ 
            this.estadoReserva = "Confirmada";
            quarto.reservar();
        } else {
            throw new IllegalStateException("Confirmacao da reserva ja foi realizada ou o quarto nao esta disponivel.");
        }
    }
    
    /**
     * Define o hóspede da reserva.
     * 
     * @param hospede o hóspede a ser atribuído
     * @throws IllegalArgumentException se o hóspede for null
     */
    public void setHospede(Hospede hospede) {
        if(hospede == null){
            throw new IllegalArgumentException("Hospede não informado.");
        }
        this.hospede = hospede;
    }

    /**
     * Define o quarto da reserva.
     * 
     * @param quarto o quarto a ser atribuído
     * @throws IllegalArgumentException se o quarto for null ou não estiver disponível
     */
    public void setQuarto(Quarto quarto) {
        if(quarto == null || quarto.estaDisponivel() == false){
            throw new IllegalArgumentException("Quarto não informado ou não disponível.");
        }
        this.quarto = quarto;
    }

    /**
     * Define a quantidade de diárias da reserva.
     * 
     * @param qtdeDiarias a quantidade de diárias a ser atribuída
     * @throws IllegalArgumentException se a quantidade de diárias for menor ou igual a zero
     */
    public void setQtdeDiarias(int qtdeDiarias) {
        if(qtdeDiarias <= 0){
            throw new IllegalArgumentException("Quantidade de diárias deve ser maior que zero.");
        }
        this.qtdeDiarias = qtdeDiarias;
    }

    /**
     * Obtém o hóspede da reserva.
     * 
     * @return o hóspede
     */
    public Hospede getHospede() {
        return hospede;
    }

    /**
     * Obtém o quarto da reserva.
     * 
     * @return o quarto
     */
    public Quarto getQuarto() {
        return quarto;
    }
    
    /**
     * Obtém a quantidade de diárias da reserva.
     * 
     * @return a quantidade de diárias
     */
    public int getQtdeDiarias() {
        return qtdeDiarias;
    }
    
    /**
     * Obtém o estado atual da reserva.
     * 
     * @return o estado da reserva (Pendente, Confirmada, Ativa ou Finalizada)
     */
    public String getEstadoReserva() {
        return estadoReserva;
    }

    /**
     * Realiza o check-in da reserva.
     * 
     * Muda o estado de "Confirmada" para "Ativa", indicando que o hóspede
     * chegou e ocupou o quarto.
     * 
     * @throws IllegalStateException se a reserva não está no estado "Confirmada"
     */
    public void realizarCheckIn(){
        if("Confirmada".equals(estadoReserva)){
            estadoReserva = "Ativa";
        } else {
            throw new IllegalStateException("Para realizar o check-in, a reserva deve ser confirmada.");
        }
    }

    /**
     * Realiza o check-out da reserva.
     * 
     * Muda o estado de "Ativa" para "Finalizada" e libera o quarto para
     * novas reservas.
     * 
     * @throws IllegalStateException se a reserva não está no estado "Ativa"
     */
    public void realizarCheckOut(){
        if("Ativa".equals(estadoReserva)){
            estadoReserva = "Finalizada";
            quarto.liberar();

        } else {
            throw new IllegalStateException("Para realizar o check-out, a reserva deve estar ativa.");
        }
    }
    
    /**
     * Calcula o valor total da reserva.
     * 
     * O valor total é calculado multiplicando o valor da diária do quarto
     * pela quantidade de diárias.
     * 
     * @return o valor total da reserva
     */
    public double calcularValorTotal() {
            return quarto.calcularDiaria() * qtdeDiarias;
    }

    /**
     * Retorna uma descrição detalhada da reserva.
     * 
     * A descrição inclui informações do hóspede, número do quarto,
     * quantidade de diárias, estado e valor total da reserva.
     * 
     * @return uma string com a descrição formatada da reserva
     */
    public String exibirDescricao() {
        return "Reserva do hóspede: " + hospede.getNome() + "\n"
                + "Quarto número: " + quarto.getNumero() + "\n"
                + "Quantidade de diárias: " + qtdeDiarias + "\n"
                + "Estado da reserva: " + estadoReserva + "\n"
                + "Valor total: R$ " + calcularValorTotal() + "\n";
    }
}