package models;

import exceptions.DadosInvalidosException;

/**
 * Representa um hóspede do hotel.
 * 
 * A classe Hospede encapsula informações pessoais de um hóspede,
 * incluindo nome, CPF e email, com validação robusta de dados.
 */
public class Hospede {
    /** Nome do hóspede */
    private String nome;
    
    /** CPF (Cadastro de Pessoas Físicas) do hóspede */
    private String cpf;
    
    /** Email do hóspede */
    private String email;    
    /**
     * Constrói um novo Hóspede com os dados fornecidos.
     * 
     * @param nome o nome do hóspede
     * @param cpf o CPF do hóspede (pode conter formatação)
     * @param email o email do hóspede
     * @throws DadosInvalidosException se o nome, CPF ou email forem inválidos
     */
    public Hospede(String nome, String cpf, String email){
        setNome(nome);
        setCpf(cpf);
        setEmail(email);
    }

    /**
     * Valida se o CPF fornecido é válido.
     * 
     * O CPF é considerado válido se:
     * - Não for null ou vazio
     * - Contiver exatamente 11 dígitos (após remoção de formatação)
     * 
     * @param cpf o CPF a ser validado (aceita formatação com pontos e hífens)
     * @return true se o CPF é válido; false caso contrário
     */
    public static boolean validaCpf(String cpf){
        if(cpf == null || cpf.trim().isEmpty()) {
            return false;
        }

        cpf = cpf.replaceAll("\\D", "");

        if(!cpf.matches("\\d{11}")){
            return false;
        }
        
        return true;
    }
    
    /**
     * Valida se o email fornecido é válido.
     * 
     * O email é considerado válido se:
     * - Não for null ou vazio
     * - Contiver o caractere '@'
     * - Não começar ou terminar com '@'
     * 
     * @param email o email a ser validado
     * @return true se o email é válido; false caso contrário
     */
    public static boolean validaEmail(String email){
        if(email == null || !email.contains("@") || email.trim().isEmpty() || email.startsWith("@") || email.endsWith("@")){
            return false;
        }
        return true;
    }

    /**
     * Define o nome do hóspede.
     * 
     * @param nome o nome a ser atribuído
     * @throws DadosInvalidosException se o nome for null ou vazio
     */
    public void setNome(String nome) throws DadosInvalidosException {
        if(nome == null || nome.trim().isEmpty()) {
            throw new DadosInvalidosException("Nome inválido");
        }
        this.nome = nome;
    }

    /**
     * Define o CPF do hóspede.
     * 
     * @param cpf o CPF a ser atribuído (aceita formatação com pontos e hífens)
     * @throws DadosInvalidosException se o CPF for inválido
     * @see #validaCpf(String)
     */
    public void setCpf(String cpf) throws DadosInvalidosException {
        if(!validaCpf(cpf)){
            throw new DadosInvalidosException("CPF inválido");
        }
        this.cpf = cpf;
    }

    /**
     * Define o email do hóspede.
     * 
     * @param email o email a ser atribuído
     * @throws DadosInvalidosException se o email for inválido
     * @see #validaEmail(String)
     */
    public void setEmail(String email) throws DadosInvalidosException {
        if(!validaEmail(email)){
            throw new DadosInvalidosException("Email inválido");
        }
        this.email = email;
    }

    /**
     * Retorna uma representação em string das informações do hóspede.
     * 
     * @return uma string contendo nome, CPF e email do hóspede
     */
    public String toString() {
        return "Nome: " + nome + ", CPF: " + cpf + ", Email: " + email;
    }

    /**
     * Obtém o email do hóspede.
     * 
     * @return o email do hóspede
     */
    public String getEmail(){
        return email;
    }

    /**
     * Obtém o nome do hóspede.
     * 
     * @return o nome do hóspede
     */
    public String getNome(){
        return nome;
    }

    /**
     * Obtém o CPF do hóspede.
     * 
     * @return o CPF do hóspede
     */
    public String getCpf(){
        return cpf;
    }
}
