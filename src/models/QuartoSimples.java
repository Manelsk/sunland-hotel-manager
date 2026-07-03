package models;

/**
 * Representa um quarto simples do hotel que pode conter ar condicionado.
 * 
 * A classe QuartoSimples é uma subclasse de Quarto que representa quartos mais
 * básicos, com a opção de possuir ar condicionado. Se o quarto possui ar condicionado,
 * o preço da diária é aumentado em 20%.
 */
public class QuartoSimples extends Quarto {
    
    /** Indica se o quarto possui ar condicionado */
    private boolean possuiArCondicionado;
    
    /**
     * Define se o quarto possui ar condicionado.
     * 
     * @param possuiArCondicionado true se o quarto possui ar condicionado; false caso contrário
     */
    public void setPossuiArCondicionado(boolean possuiArCondicionado) {
        this.possuiArCondicionado = possuiArCondicionado;
    }

    /**
     * Verifica se o quarto possui ar condicionado.
     * 
     * @return true se o quarto possui ar condicionado; false caso contrário
     */
    public boolean isPossuiArCondicionado() {
        return possuiArCondicionado;
    }

    /**
     * Constrói um novo Quarto Simples com as características especificadas.
     * 
     * @param numero o número único do quarto
     * @param precoBase o preço base da diária
     * @param capacidade a capacidade de hóspedes
     * @param possuiArCondicionado true se o quarto possui ar condicionado; false caso contrário
     * @throws IllegalArgumentException se os parâmetros herdados forem inválidos
     */
    public QuartoSimples(int numero, double precoBase, int capacidade, boolean possuiArCondicionado) {
        super(numero, precoBase, capacidade);
        this.possuiArCondicionado = possuiArCondicionado;
    }

    /**
     * Calcula o valor da diária do quarto simples.
     * 
     * Se o quarto possui ar condicionado, o preço é aumentado em 20%.
     * 
     * @return o valor calculado da diária
     */
    @Override
    public double calcularDiaria(){
        if(isPossuiArCondicionado()){
            return getPrecoBase() * 1.2;
        }
        return getPrecoBase();
    }

    /**
     * Retorna uma descrição detalhada do quarto simples.
     * 
     * A descrição inclui número do quarto, capacidade, disponibilidade de ar
     * condicionado, preço da diária e status de disponibilidade.
     * 
     * @return uma string com a descrição formatada do quarto
     */
    @Override
    public String exibirDescricao(){
        return "======== Quarto Simples ========"
        +"\n Numero" + getNumero() 
        + "\nCapacidade: " + getCapacidade() + " pessoas." 
        + "\nAr condicionado: " + (possuiArCondicionado ? "Sim\n" : "Não\n")
        + "Preço da diária: R$ " + calcularDiaria() + "\n"
        + "Disponibilidade: " + (estaDisponivel() ? "Disponível" : "Indisponível") + "\n";
    }
}