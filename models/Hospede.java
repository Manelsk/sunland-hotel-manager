package models;

import exceptions.DadosInvalidosException;

public class Hospede {
    private String nome;
    private String cpf;
    private String email;

    public Hospede(String nome, String cpf, String email){
        setNome(nome);
        setCpf(cpf);
        setEmail(email);
    }

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
    
    public static boolean validaEmail(String email){
        if(email == null || !email.contains("@") || email.trim().isEmpty() || email.startsWith("@") || email.endsWith("@")){
            return false;
        }
        return true;
    }

    public void setNome(String nome) throws DadosInvalidosException {
        if(nome == null || nome.trim().isEmpty()) {
            throw new DadosInvalidosException("Nome inválido");
        }
        this.nome = nome;
    }

    public void setCpf(String cpf) throws DadosInvalidosException {
        if(!validaCpf(cpf)){
            throw new DadosInvalidosException("CPF inválido");
        }
        this.cpf = cpf;
    }

    public void setEmail(String email) throws DadosInvalidosException {
        if(!validaEmail(email)){
            throw new DadosInvalidosException("Email inválido");
        }
        this.email = email;
    }

    public String toString() {
        return "Nome: " + nome + ", CPF: " + cpf + ", Email: " + email;
    }

    public String getEmail(){
        return email;
    }

    public String getNome(){
        return nome;
    }

    public String getCpf(){
        return cpf;
    }
}
