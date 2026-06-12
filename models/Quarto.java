package models;

public abstract class Quarto implements Reservavel {
    private int numero;
    private double precoBase;
    private int capacidade;
    private String status;

    public Quarto(int numero, double precoBase, int capacidade) {
        setNumero(numero);
        setPrecoBase(precoBase);
        setStatus("disponivel");
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
        if(numero>=0){
            this.numero = numero; 
        }
    }
    
    public void setPrecoBase(double precoBase) {
        if(precoBase>=0){
            this.precoBase = precoBase;
        }
    }

    public void setCapacidade(int capacidade) {
        if(capacidade>0){
            this.capacidade = capacidade;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean estaDisponivel() {
        return "disponivel".equals(status);
    }

    public void reservar(){
        if("disponivel".equals(status)){
            System.out.println("Quarto " + getNumero() + " reservado.");
            setStatus("reservado");
        }else{
            System.out.println("Quarto "+ getNumero() + " esta indisponível");
        }
    }

    public void liberar(){
        if("reservado".equals(status)){
            System.out.println("Quarto " + getNumero() + " liberado.");
            setStatus("disponivel");
        }
    }

    public abstract double calcularDiaria();

    public abstract String exibirDescricao();
}