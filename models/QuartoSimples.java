public class QuartoSimples extends Quarto {
    
    private boolean possuiArCondicionado;
    
    public void setPossuiArCondicionado(boolean possuiArCondicionado) {
        this.possuiArCondicionado = possuiArCondicionado;
    }

    public boolean temArCondicionado() {
        return possuiArCondicionado;
    }

    public QuartoSimples(int numero, double precoBase, int capacidade, boolean possuiArCondicionado) {
        super(numero, precoBase, capacidade, "disponivel");
        this.possuiArCondicionado = possuiArCondicionado;
    }
}