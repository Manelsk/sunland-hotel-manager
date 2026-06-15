package models;

public class Hospede {
    private String nome;
    private String cpf;
    private String email;

    public void setNome(String nome) {
        if(nome == null) {
            throw new IllegalArgumentException("Nome inválido");
        }
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        if(cpf == null) {
            throw new IllegalArgumentException("CPF inválido");
        }
        if(cpf.length() != 11) {
            throw new IllegalArgumentException("CPF deve conter 11 dígitos");
        }
        this.cpf = cpf;
    }
}
