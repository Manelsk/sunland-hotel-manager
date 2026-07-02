package models;

public class Reserva{
    private Hospede hospede;
    private Quarto quarto;
    private int qtdeDiarias;
    private String estadoReserva;

    public Reserva(Hospede hospede, Quarto quarto, int qtdeDiarias) {
        setHospede(hospede);
        setQuarto(quarto);
        setQtdeDiarias(qtdeDiarias);
        this.estadoReserva = "Pendente";
    }

    public void confirmaReserva() {
        if("Pendente".equals(estadoReserva)){ 
            this.estadoReserva = "Confirmada";
            quarto.reservar();
        } else {
            throw new IllegalStateException("Confirmacao da reserva ja foi realizada ou o quarto nao esta disponivel.");
        }
    }
    
    public void setHospede(Hospede hospede) {
        if(hospede == null){
            throw new IllegalArgumentException("Hospede não informado.");
        }
        this.hospede = hospede;
    }

    public void setQuarto(Quarto quarto) {
        if(quarto == null || quarto.estaDisponivel() == false){
            throw new IllegalArgumentException("Quarto não informado ou não disponível.");
        }
        this.quarto = quarto;
    }

    public void setQtdeDiarias(int qtdeDiarias) {
        if(qtdeDiarias <= 0){
            throw new IllegalArgumentException("Quantidade de diárias deve ser maior que zero.");
        }
        this.qtdeDiarias = qtdeDiarias;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public Quarto getQuarto() {
        return quarto;
    }
    
    public int getQtdeDiarias() {
        return qtdeDiarias;
    }
    
    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void realizarCheckIn(){
        if("Confirmada".equals(estadoReserva)){
            estadoReserva = "Ativa";
        } else {
            throw new IllegalStateException("Para realizar o check-in, a reserva deve ser confirmada.");
        }
    }

    public void realizarCheckOut(){
        if("Ativa".equals(estadoReserva)){
            estadoReserva = "Finalizada";
            quarto.liberar();

        } else {
            throw new IllegalStateException("Para realizar o check-out, a reserva deve estar ativa.");
        }
    }
    
    public double calcularValorTotal() {
            return quarto.calcularDiaria() * qtdeDiarias;
    }

    public String exibirDescricao() {
        return "Reserva do hóspede: " + hospede.getNome() + "\n"
                + "Quarto número: " + quarto.getNumero() + "\n"
                + "Quantidade de diárias: " + qtdeDiarias + "\n"
                + "Estado da reserva: " + estadoReserva + "\n"
                + "Valor total: R$ " + calcularValorTotal() + "\n";
    }
}