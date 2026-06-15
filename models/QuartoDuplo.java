package models;

public class QuartoDuplo extends Quarto {
    private String tipoCama;
    private boolean possuiVaranda;

    public void setTipoCama(String tipoCama){
        if(!"Casal".equals(getTipoCama())){
            throw new IllegalArgumentException ("Tipo de cama inválido");
        }
        this.tipoCama = tipoCama;
    }

    public String getTipoCama(){
        return tipoCama;
    }

    public void setTemVaranda(boolean possuiVaranda){
        this.possuiVaranda = possuiVaranda;
    }

    public boolean getTemVaranda(){
        return possuiVaranda;
    }

    @Override
    public double calcularDiaria(){
        if()
    }

}
