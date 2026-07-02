package models;

public class QuartoDuplo extends Quarto {
    private String tipoCama;
    private boolean possuiVaranda;

    public QuartoDuplo(int numero, double precoBase, int capacidade, String tipoCama, Boolean possuiVaranda){
        super(numero, precoBase, capacidade);
        setPossuiVaranda(possuiVaranda);
        setTipoCama(tipoCama);
    }

    public void setTipoCama(String tipoCama){
        if(!"Casal".equals(tipoCama) && !"Solteiro".equals(tipoCama)){
            throw new IllegalArgumentException ("Tipo de cama inválido");
        }
        this.tipoCama = tipoCama;
    }

    public String getTipoCama(){
        return tipoCama;
    }

    public void setPossuiVaranda(boolean possuiVaranda){
        this.possuiVaranda = possuiVaranda;
    }

    public boolean isPossuiVaranda(){
        return possuiVaranda;
    }

    @Override
    public double calcularDiaria(){
        if(isPossuiVaranda()){
            return getPrecoBase() * 1.2;
        }
        return getPrecoBase();
    }

    @Override
    public String exibirDescricao(){
        return "======== Quarto Duplo com cama de "+ getTipoCama() + " ========"
        +"\nNúmero: "+ getNumero()
        +"\nPossui varanda? "+ (isPossuiVaranda() ? "Sim\n" : "Nao\n")
        + "Preço da diária: R$ " + calcularDiaria() + "\n"
        + "Capacidade: " + getCapacidade() + " pessoas.\n"
        + "Disponibilidade: " + (estaDisponivel() ? "Disponível" : "Indisponível") + "\n";
    }
}
