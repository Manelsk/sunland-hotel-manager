package models;

public class Suite extends Quarto{
    
    private String tipoSuite;
    private static final String PRESIDENCIAL = "Presidencial";
    private static final String EXECUTIVA = "Executiva";

    public String getTipoSuite() {
        return tipoSuite;
    }

    public void setTipoSuite(String tipoSuite) {
        if(!PRESIDENCIAL.equals(tipoSuite) && !EXECUTIVA.equals(tipoSuite)){
            throw new IllegalArgumentException("Tipo de Suite inválido");
        }
        this.tipoSuite = tipoSuite;
    }

    public Suite(int numero, double precoBase, int capacidade, String tipoSuite) {
        super(numero, precoBase, capacidade);
        setTipoSuite(tipoSuite);
    }

    @Override
    public double calcularDiaria() {
        if(PRESIDENCIAL.equals(tipoSuite)) {
            return getPrecoBase() * 2.0;
        } else if (EXECUTIVA.equals(tipoSuite)) {
            return getPrecoBase() * 1.5;
        } else {
            return getPrecoBase();
        }
    }

    @Override
public String exibirDescricao() {
    return "======== Suíte " + getTipoSuite() + "========"
            + "\nnúmero: " + getNumero() 
            +"\nCom capacidade para: " + getCapacidade() + " pessoas." 
            +"\nPreço da diária: R$ " + calcularDiaria()
            + "\nDisponibilidade: " + (estaDisponivel() ? "Disponível" : "Indisponível") + "\n";
    }
}