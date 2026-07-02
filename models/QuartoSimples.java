package models;

public class QuartoSimples extends Quarto {
    
    private boolean possuiArCondicionado;
    
    public void setPossuiArCondicionado(boolean possuiArCondicionado) {
        this.possuiArCondicionado = possuiArCondicionado;
    }

    public boolean isPossuiArCondicionado() {
        return possuiArCondicionado;
    }

    public QuartoSimples(int numero, double precoBase, int capacidade, boolean possuiArCondicionado) {
        super(numero, precoBase, capacidade);
        this.possuiArCondicionado = possuiArCondicionado;
    }

    @Override
    public double calcularDiaria(){
        if(isPossuiArCondicionado()){
            return getPrecoBase() * 1.2;
        }
        return getPrecoBase();
    }

    @Override
    public String exibirDescricao(){
        return "======== Quarto Simples número: " + getNumero() + "========"
        + "\nCapacidade: " + getCapacidade() + " pessoas." 
        + "\nAr condicionado: " + (possuiArCondicionado ? "Sim\n" : "Não\n")
        + "Preço da diária: R$ " + calcularDiaria() + "\n"
        + "Disponibilidade: " + (estaDisponivel() ? "Disponível" : "Indisponível") + "\n";
    }
}