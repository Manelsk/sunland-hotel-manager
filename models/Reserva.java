package models;

public class Reserva{
    private Hospede hospede;
    private Quarto quarto;
    private int qtdeDiarias;
    private String estadoReserva;

    public void realizarCheckIn(){
        if("Confirmada".equals(estadoReserva)){
            estadoReserva = "Ativa";
        } else {
            throw new IllegalStateException("Reserva não pode ser confirmada.");
        }
    }

    public void realizarCheckOut(){
        if("Ativa".equals(estadoReserva)){
            estadoReserva = "Finalizada";
            quarto.liberar();

        } else {
            throw new IllegalStateException("Reserva não pode ser finalizada.");
        }
    }
    
    public double calculaValorTotal() {
        return quarto.calcularDiaria() * qtdeDiarias;
    }
}