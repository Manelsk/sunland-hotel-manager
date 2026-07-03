package models;
import interfaces.Reservavel;

/**
 * Representa um quarto do hotel com funcionalidades de reserva.
 * 
 * A classe abstrata Quarto define a estrutura base para diferentes tipos de quartos,
 * incluindo gerenciamento de status de disponibilidade, preço e capacidade.
 * Subclasses devem implementar o cálculo específico de diária e descrição.
 */
public abstract class Quarto implements Reservavel {
    
    /** Número único que identifica o quarto */
    private int numero;
    
    /** Preço base da diária do quarto */
    private double precoBase;
    
    /** Capacidade de hóspedes do quarto */
    private int capacidade;
    
    /** Status do quarto: "disponivel" ou "reservado" */
    private String status;

    /**
     * Constrói um novo Quarto com número, preço base e capacidade.
     * 
     * O status inicial do quarto é definido como "disponível".
     * 
     * @param numero o número único do quarto
     * @param precoBase o preço base da diária
     * @param capacidade a capacidade de hóspedes
     * @throws IllegalArgumentException se o número ou preço forem negativos,
     *         ou se a capacidade for negativa
     */
    public Quarto(int numero, double precoBase, int capacidade) {
        setNumero(numero);
        setPrecoBase(precoBase);
        setCapacidade(capacidade);
        setStatus("disponivel");
    }

    /**
     * Obtém o número do quarto.
     * 
     * @return o número único do quarto
     */
    public int getNumero(){
        return numero;
    }

    /**
     * Obtém o preço base da diária do quarto.
     * 
     * @return o preço base
     */
    public double getPrecoBase() {
        return precoBase;
    }

    /**
     * Obtém a capacidade de hóspedes do quarto.
     * 
     * @return a capacidade
     */
    public int getCapacidade() {
        return capacidade;
    }

    /**
     * Obtém o status atual do quarto.
     * 
     * @return o status ("disponivel" ou "reservado")
     */
    public String getStatus() {
        return status;
    }

    /**
     * Define o número do quarto.
     * 
     * @param numero o número a ser atribuído
     * @throws IllegalArgumentException se o número for negativo
     */
    public void setNumero(int numero) {
        if(numero<0){
            throw new IllegalArgumentException("Numero inválido.");
        }
        
        this.numero = numero; 
    }
    
    /**
     * Define o preço base da diária do quarto.
     * 
     * @param precoBase o preço a ser atribuído
     * @throws IllegalArgumentException se o preço for negativo
     */
    public void setPrecoBase(double precoBase) {
        if(precoBase<0){
            throw new IllegalArgumentException("Preco não pode ser negativo.");
        }

        this.precoBase = precoBase;
    }

    /**
     * Define a capacidade de hóspedes do quarto.
     * 
     * @param capacidade a capacidade a ser atribuída
     * @throws IllegalArgumentException se a capacidade for negativa
     */
    public void setCapacidade(int capacidade) {
        if(capacidade<0){
            throw new IllegalArgumentException("Capacidade deve ser maior que zero.");
        }
        this.capacidade = capacidade;
    }

    /**
     * Define o status do quarto.
     * 
     * @param status o status a ser atribuído ("disponivel" ou "reservado")
     * @throws IllegalArgumentException se o status for inválido
     */
    private void setStatus(String status) {
        if(!"disponivel".equals(status) && !"reservado".equals(status)){
            throw new IllegalArgumentException("Status inválido.");
        }
        this.status = status;
    }

    /**
     * Verifica se o quarto está disponível para reserva.
     * 
     * @return true se o quarto está disponível; false caso contrário
     */
    public boolean estaDisponivel() {
        return "disponivel".equals(status);
    }

    /**
     * Marca o quarto como reservado.
     * 
     * O quarto só pode ser reservado se estiver disponível.
     */
    public void reservar(){
        if("disponivel".equals(status)){
            setStatus("reservado");
        }
    }

    /**
     * Libera o quarto, marcando como disponível.
     * 
     * O quarto só pode ser liberado se estiver reservado.
     */
    public void liberar(){
        if("reservado".equals(status)){
            setStatus("disponivel");
        }
    }

    /**
     * Calcula o valor da diária do quarto.
     * 
     * Método abstrato que deve ser implementado pelas subclasses
     * com a lógica específica de cálculo de preço.
     * 
     * @return o valor da diária
     */
    public abstract double calcularDiaria();

    /**
     * Retorna uma descrição detalhada do quarto.
     * 
     * Método abstrato que deve ser implementado pelas subclasses
     * com informações específicas sobre o tipo de quarto.
     * 
     * @return uma string com a descrição do quarto.
     */
    public abstract String exibirDescricao();
}