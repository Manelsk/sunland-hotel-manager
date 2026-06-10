public abstract class Quarto implements Reservavel {
    private int numero;
    private double precoBase;
    private int capacidade;
    private String status;

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

    public abstract double calcularDiaria();

    public abstract String exibirDescricao();
}