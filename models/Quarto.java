package models;

public abstract class Quarto implements Reservavel {
    private int numero;
    private double precoBase;
    private int capacidade;
    private String status;

    public Quarto(int numero, double precoBase, int capacidade) {
        setNumero(numero);
        setPrecoBase(precoBase);
        setCapacidade(capacidade);
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
        if(numero<0){
            throw new IllegalArgumentException("Numero inválido.");
        }
        
        this.numero = numero; 
    }
    
    public void setPrecoBase(double precoBase) {
        if(precoBase<0){
            throw new IllegalArgumentException("Preco não pode ser negativo.");
        }

        this.precoBase = precoBase;
    }

    public void setCapacidade(int capacidade) {
        if(capacidade<0){
            throw new IllegalArgumentException("Capacidade deve ser maior que zero.");
        }
        this.capacidade = capacidade;
    }

    private void setStatus(String status) {
        if(!"disponivel".equals(status) && !"reservado".equals(status)){
            throw new IllegalArgumentException("Status inválido.");
        }
        this.status = status;
    }

    public boolean estaDisponivel() {
        return "disponivel".equals(status);
    }

    public void reservar(){
        if("disponivel".equals(status)){
            setStatus("reservado");
        }
    }

    public void liberar(){
        if("reservado".equals(status)){
            setStatus("disponivel");
        }
    }

    public abstract double calcularDiaria();

    public abstract String exibirDescricao();
}