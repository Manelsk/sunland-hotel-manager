package models;

public abstract class Quarto implements Reservavel {
    private int numero;
    private double precoBase;
    private int capacidade;
    private String status;

    public Quarto(int numero, double precoBase, int capacidade) {
        this.numero = numero;
        this.precoBase = precoBase;
        this.capacidade = capacidade;
        this.status = "disponivel";
    }

    public int getNumero(){
        return numero;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public String getStatus() {
        return status;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean estaDisponivel() {
        if (status == null) {
            return false;
        }
        return "disponivel".equals(status);
    }

    public void reservar(){
        System.out.println("Quarto " + getNumero() + " reservado.");
        setStatus("reservado");
    }

    public void liberar(){
        System.out.println("Quarto " + getNumero() + " liberado.");
        setStatus("disponivel");
    }

    public abstract double calcularDiaria();

    public abstract String exibirDescricao();
}