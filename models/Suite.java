public class Suite extends Quarto{
    
    private String tipoSuite;

    public String getTipoSuite() {
        return tipoSuite;
    }

    public void setTipoSuite(String tipoSuite) {
        this.tipoSuite = tipoSuite;
    }

    public Suite(int numero, double precoBase, int capacidade, String tipoSuite) {
        super(numero, precoBase, capacidade, "disponivel");
        this.tipoSuite = tipoSuite;
    }

    @Override
    public double calcularDiaria() {
        if("Presidencial".equals(tipoSuite)) {
            return getPrecoBase() * 2.0;
        } else if ("Executiva".equals(tipoSuite)) {
            return getPrecoBase() * 1.5;
        } else {
            return getPrecoBase();
        }
    }

    @Override
public String exibirDescricao() {
    return "Suíte " + getTipoSuite() + " número: " + getNumero() +
           "\nCom capacidade para: " + getCapacidade() + " pessoas.";
    }
}