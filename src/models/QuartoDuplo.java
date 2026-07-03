package models;

/**
 * Representa um quarto duplo do hotel que pode conter varanda.
 * 
 * A classe QuartoDuplo é uma subclasse de Quarto que adiciona características
 * específicas de um quarto duplo, como tipo de cama (Casal ou Solteiro) e
 * possibilidade de varanda. O preço da diária pode aumentar 20% se o quarto
 * possui varanda.
 */
public class QuartoDuplo extends Quarto {
    /** Tipo de cama do quarto: "Casal" ou "Solteiro" */
    private String tipoCama;
    
    /** Indica se o quarto possui varanda */
    private boolean possuiVaranda;

    /**
     * Constrói um novo Quarto Duplo com as características especificadas.
     * 
     * @param numero o número único do quarto
     * @param precoBase o preço base da diária
     * @param capacidade a capacidade de hóspedes
     * @param tipoCama o tipo de cama ("Casal" ou "Solteiro")
     * @param possuiVaranda true se o quarto possui varanda; false caso contrário
     * @throws IllegalArgumentException se o tipo de cama for inválido ou se os
     *         parâmetros herdados forem inválidos
     */
    public QuartoDuplo(int numero, double precoBase, int capacidade, String tipoCama, Boolean possuiVaranda){
        super(numero, precoBase, capacidade);
        setPossuiVaranda(possuiVaranda);
        setTipoCama(tipoCama);
    }

    /**
     * Define o tipo de cama do quarto.
     * 
     * @param tipoCama o tipo de cama ("Casal" ou "Solteiro")
     * @throws IllegalArgumentException se o tipo de cama for inválido
     */
    public void setTipoCama(String tipoCama){
        if(!"Casal".equals(tipoCama) && !"Solteiro".equals(tipoCama)){
            throw new IllegalArgumentException ("Tipo de cama inválido");
        }
        this.tipoCama = tipoCama;
    }

    /**
     * Obtém o tipo de cama do quarto.
     * 
     * @return o tipo de cama ("Casal" ou "Solteiro")
     */
    public String getTipoCama(){
        return tipoCama;
    }

    /**
     * Define se o quarto possui varanda.
     * 
     * @param possuiVaranda true se o quarto possui varanda; false caso contrário
     */
    public void setPossuiVaranda(boolean possuiVaranda){
        this.possuiVaranda = possuiVaranda;
    }

    /**
     * Verifica se o quarto possui varanda.
     * 
     * @return true se o quarto possui varanda; false caso contrário
     */
    public boolean isPossuiVaranda(){
        return possuiVaranda;
    }

    /**
     * Calcula o valor da diária do quarto duplo.
     * 
     * Se o quarto possui varanda, o preço é aumentado em 20%.
     * 
     * @return o valor calculado da diária
     */
    @Override
    public double calcularDiaria(){
        if(isPossuiVaranda()){
            return getPrecoBase() * 1.2;
        }
        return getPrecoBase();
    }

    /**
     * Retorna uma descrição detalhada do quarto duplo.
     * 
     * A descrição inclui o tipo de cama, número do quarto, disponibilidade
     * de varanda, preço da diária, capacidade e status de disponibilidade.
     * 
     * @return uma string com a descrição formatada do quarto
     */
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
