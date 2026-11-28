package com.sunland.hotel.models;

import com.sunland.hotel.enums.StatusQuarto;
import com.sunland.hotel.exceptions.DadosInvalidosException;
import com.sunland.hotel.exceptions.DadosNaoInformadosException;
import com.sunland.hotel.interfaces.Reservavel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.UUID;

/**
 * Classe base abstrata que representa um quarto do hotel.
 */
public abstract class Quarto implements Reservavel {

    private final UUID id;
    private int numero;
    private BigDecimal precoBase;
    private int capacidade;
    private StatusQuarto status;

    /**
     * Constrói um novo quarto com status inicial {@link StatusQuarto#DISPONIVEL}.
     *
     * @param numero número positivo único do quarto
     * @param precoBase preço base positivo da diária
     * @param capacidade capacidade positiva de hóspedes
     */
    protected Quarto(int numero, BigDecimal precoBase, int capacidade) {
        this.id = UUID.randomUUID();
        setNumero(numero);
        setPrecoBase(precoBase);
        setCapacidade(capacidade);
        this.status = StatusQuarto.DISPONIVEL;
    }

    public UUID getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        if (numero <= 0) {
            throw new DadosInvalidosException("Número do quarto deve ser maior que zero.");
        }
        this.numero = numero;
    }

    public BigDecimal getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(BigDecimal precoBase) {
        if (precoBase == null) {
            throw new DadosNaoInformadosException("Preço base deve ser informado.");
        }
        if (precoBase.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DadosInvalidosException("Preço base deve ser maior que zero.");
        }
        this.precoBase = precoBase.setScale(2, RoundingMode.HALF_UP);
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        if (capacidade <= 0) {
            throw new DadosInvalidosException("Capacidade deve ser maior que zero.");
        }
        this.capacidade = capacidade;
    }

    public StatusQuarto getStatus() {
        return status;
    }

    @Override
    public boolean estaDisponivel() {
        return status == StatusQuarto.DISPONIVEL;
    }

    @Override
    public void ocupar() {
        if (status != StatusQuarto.DISPONIVEL) {
            throw new IllegalStateException(
                    "Quarto " + numero + " não pode ser ocupado pois está " + status.getDescricao() + ".");
        }
        this.status = StatusQuarto.OCUPADO;
    }

    @Override
    public void liberar() {
        if (status != StatusQuarto.OCUPADO) {
            throw new IllegalStateException(
                    "Quarto " + numero + " não pode ser liberado pois está " + status.getDescricao() + ".");
        }
        this.status = StatusQuarto.DISPONIVEL;
    }

    /**
     * Coloca um quarto disponível em manutenção.
     *
     * @throws IllegalStateException se o quarto não estiver disponível
     */
    public void colocarEmManutencao() {
        if (status != StatusQuarto.DISPONIVEL) {
            throw new IllegalStateException(
                    "Quarto " + numero + " não pode entrar em manutenção pois está " + status.getDescricao() + ".");
        }
        this.status = StatusQuarto.MANUTENCAO;
    }

    /**
     * Retorna um quarto em manutenção para disponível.
     *
     * @throws IllegalStateException se o quarto não estiver em manutenção
     */
    public void finalizarManutencao() {
        if (status != StatusQuarto.MANUTENCAO) {
            throw new IllegalStateException(
                    "Quarto " + numero + " não está em manutenção.");
        }
        this.status = StatusQuarto.DISPONIVEL;
    }

    @Override
    public abstract BigDecimal calcularDiaria();

    /**
     * Retorna uma descrição detalhada do quarto.
     *
     * @return texto formatado descrevendo o quarto
     */
    public abstract String exibirDescricao();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Quarto other)) {
            return false;
        }
        return numero == other.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}
