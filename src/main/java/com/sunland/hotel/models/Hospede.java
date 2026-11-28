package com.sunland.hotel.models;

import com.sunland.hotel.exceptions.DadosInvalidosException;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Representa um hóspede do hotel.
 *
 * A classe {@code Hospede} encapsula identificador único, nome, CPF normalizado
 * (com validação de formato e dígitos verificadores) e e-mail válido.
 */
public class Hospede {
    private static final Pattern NAME_PATTERN =
            Pattern.compile("^\\p{L}+(?:[ '\\-]\\p{L}+)*$");
    private static final Pattern CPF_FORMAT_PATTERN =
            Pattern.compile("^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$");
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9](?:[A-Za-z0-9+_.-]*[A-Za-z0-9])?@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z]{2,}$");
    private static final Pattern REPEATED_DIGITS_PATTERN = Pattern.compile("^(\\d)\\1{10}$");

    private final UUID id;
    private String nome;
    private String cpf;
    private String email;

    /**
     * Constrói um novo hóspede validando e normalizando seus dados.
     *
     * @param nome nome completo do hóspede (somente letras, espaços, hífen ou apóstrofo entre letras)
     * @param cpf CPF válido (11 dígitos ou XXX.XXX.XXX-XX)
     * @param email endereço de e-mail válido
     * @throws DadosInvalidosException se nome, CPF ou email forem inválidos
     */
    public Hospede(String nome, String cpf, String email) {
        this.id = UUID.randomUUID();
        setNome(nome);
        setCpf(cpf);
        setEmail(email);
    }

    /**
     * Valida e normaliza o nome do hóspede, aceitando apenas letras Unicode
     * com espaços, apóstrofos ou hífens entre letras.
     *
     * @param nome nome informado
     * @return {@code true} se o nome for válido; {@code false} caso contrário
     */
    public static boolean validaNome(String nome) {
        if (nome == null) {
            return false;
        }
        String normalizado = nome.trim().replaceAll("\\s+", " ");
        if (normalizado.length() < 3 || normalizado.length() > 100) {
            return false;
        }
        if (!NAME_PATTERN.matcher(normalizado).matches()) {
            return false;
        }
        String apenasLetras = normalizado.replaceAll("[^\\p{L}]", "");
        return apenasLetras.length() >= 3;
    }

    /**
     * Remove caracteres não numéricos de um CPF.
     *
     * @param cpf CPF com ou sem formatação
     * @return string contendo apenas os dígitos, ou string vazia se {@code cpf} for null
     */
    public static String normalizarCpf(String cpf) {
        if (cpf == null) {
            return "";
        }
        return cpf.replaceAll("\\D", "");
    }

    /**
     * Valida o CPF verificando o formato aceito (11 dígitos ou {@code XXX.XXX.XXX-XX}),
     * rejeitando sequências repetidas e verificando os dois dígitos verificadores oficiais (módulo 11).
     *
     * @param cpf CPF a ser validado
     * @return {@code true} se válido; {@code false} caso contrário
     */
    public static boolean validaCpf(String cpf) {
        if (cpf == null) {
            return false;
        }
        String texto = cpf.trim();
        if (texto.isEmpty() || !CPF_FORMAT_PATTERN.matcher(texto).matches()) {
            return false;
        }

        String digitos = normalizarCpf(texto);
        if (REPEATED_DIGITS_PATTERN.matcher(digitos).matches()) {
            return false;
        }

        int primeiroDigito = calcularDigitoVerificador(digitos, 9, 10);
        if (primeiroDigito != Character.getNumericValue(digitos.charAt(9))) {
            return false;
        }

        int segundoDigito = calcularDigitoVerificador(digitos, 10, 11);
        return segundoDigito == Character.getNumericValue(digitos.charAt(10));
    }

    private static int calcularDigitoVerificador(String digitos, int tamanhoBase, int pesoInicial) {
        int soma = 0;
        for (int i = 0; i < tamanhoBase; i++) {
            int num = Character.getNumericValue(digitos.charAt(i));
            soma += num * (pesoInicial - i);
        }
        int resto = (soma * 10) % 11;
        return (resto == 10) ? 0 : resto;
    }

    /**
     * Valida se o e-mail atende ao formato padrão estrito {@code usuario@dominio.tld}.
     *
     * @param email endereço de e-mail a validar
     * @return {@code true} se válido; {@code false} caso contrário
     */
    public static boolean validaEmail(String email) {
        if (email == null) {
            return false;
        }
        String normalizado = email.trim();
        if (normalizado.isEmpty() || normalizado.length() > 254 || normalizado.contains("..")) {
            return false;
        }
        return EMAIL_PATTERN.matcher(normalizado).matches();
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (!validaNome(nome)) {
            throw new DadosInvalidosException(
                    "Nome inválido: use apenas letras (3 a 100 caracteres, sem números ou símbolos).");
        }
        this.nome = nome.trim().replaceAll("\\s+", " ");
    }

    /**
     * Retorna o CPF normalizado (somente os 11 dígitos).
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Retorna o CPF formatado no padrão {@code XXX.XXX.XXX-XX}.
     */
    public String getCpfFormatado() {
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "."
                + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }

    public void setCpf(String cpf) {
        if (!validaCpf(cpf)) {
            throw new DadosInvalidosException("CPF inválido.");
        }
        this.cpf = normalizarCpf(cpf);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!validaEmail(email)) {
            throw new DadosInvalidosException("Email inválido.");
        }
        this.email = email.trim().toLowerCase();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Hospede other)) {
            return false;
        }
        return Objects.equals(cpf, other.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return "ID: " + id.toString().substring(0, 8).toUpperCase()
                + " | Nome: " + nome
                + " | CPF: " + getCpfFormatado()
                + " | Email: " + email;
    }
}
