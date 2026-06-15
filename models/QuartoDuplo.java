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
        if(!"Casal".equals(getTipoCama())){
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
        return "Quarto Duplo com cama de "+ getTipoCama() 
        +"\nNúmero "+ getNumero()
        +"\nPossui varanda? "+ (isPossuiVaranda() ? "Sim" : "Nao");
    }

}
