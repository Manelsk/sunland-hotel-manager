public class QuartoSimples extends Quarto {
    
    private boolean possuiArCondicionado;
    
    public void setPossuiArCondicionado(boolean possuiArCondicionado) {
        this.possuiArCondicionado = possuiArCondicionado;
    }

    public boolean isPossuiArCondicionado() {
        return possuiArCondicionado;
    }

    public QuartoSimples(int numero, double precoBase, int capacidade, boolean possuiArCondicionado) {
        super(numero, precoBase, capacidade, "disponivel");
        this.possuiArCondicionado = possuiArCondicionado;
    }

    @Override
    public double calcularDiaria(){
        if(getArCondicionado()){
            return getPrecoBase() * 1.5;
        }
        return getPrecoBase();
    }
}