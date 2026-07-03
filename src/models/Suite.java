package models;

/**
 * Representa uma suíte premium do hotel.
 * 
 * A classe Suite é uma subclasse de Quarto que representa suítes de alto padrão,
 * oferecendo dois tipos: Presidencial e Executiva. Cada tipo possui um multiplicador
 * específico de preço sobre o preço base, sendo a Presidencial a mais cara.
 * 
 * @author Sistema Sunland Hotel Manager
 * @version 1.0
 * @since 1.0
 */
public class Suite extends Quarto{
    
    /** Tipo da suíte (Presidencial ou Executiva) */
    private String tipoSuite;
    
    /** Constante para suíte tipo Presidencial */
    private static final String PRESIDENCIAL = "Presidencial";
    
    /** Constante para suíte tipo Executiva */
    private static final String EXECUTIVA = "Executiva";

    /**
     * Obtém o tipo da suíte.
     * 
     * @return o tipo da suíte ("Presidencial" ou "Executiva")
     */
    public String getTipoSuite() {
        return tipoSuite;
    }

    /**
     * Define o tipo da suíte.
     * 
     * @param tipoSuite o tipo de suíte a ser atribuído ("Presidencial" ou "Executiva")
     * @throws IllegalArgumentException se o tipo de suíte for inválido
     */
    public void setTipoSuite(String tipoSuite) {
        if(!PRESIDENCIAL.equals(tipoSuite) && !EXECUTIVA.equals(tipoSuite)){
            throw new IllegalArgumentException("Tipo de Suite inválido");
        }
        this.tipoSuite = tipoSuite;
    }

    /**
     * Constrói uma nova Suite com as características especificadas.
     * 
     * @param numero o número único da suíte
     * @param precoBase o preço base da diária
     * @param capacidade a capacidade de hóspedes
     * @param tipoSuite o tipo de suíte ("Presidencial" ou "Executiva")
     * @throws IllegalArgumentException se o tipo de suíte for inválido ou se os
     *         parâmetros herdados forem inválidos
     */
    public Suite(int numero, double precoBase, int capacidade, String tipoSuite) {
        super(numero, precoBase, capacidade);
        setTipoSuite(tipoSuite);
    }

    /**
     * Calcula o valor da diária da suíte.
     * 
     * - Presidencial: preço base × 2.0 (200% do preço base)
     * - Executiva: preço base × 1.5 (150% do preço base)
     * 
     * @return o valor calculado da diária
     */
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

    /**
     * Retorna uma descrição detalhada da suíte.
     * 
     * A descrição inclui o tipo de suíte, número, capacidade, preço da diária
     * e status de disponibilidade.
     * 
     * @return uma string com a descrição formatada da suíte
     */
    @Override
public String exibirDescricao() {
    return "======== Suíte " + getTipoSuite() + "========"
            + "\nNúmero: " + getNumero() 
            +"\nCapacidade: " + getCapacidade() + " pessoas." 
            +"\nPreço da diária: R$ " + calcularDiaria()
            + "\nDisponibilidade: " + (estaDisponivel() ? "Disponível" : "Indisponível") + "\n";
    }
}