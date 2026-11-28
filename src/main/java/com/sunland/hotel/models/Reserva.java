package com.sunland.hotel.models;

import com.sunland.hotel.enums.EstadoReserva;
import com.sunland.hotel.enums.StatusQuarto;
import com.sunland.hotel.exceptions.ConflitoReservaException;
import com.sunland.hotel.exceptions.DadosInvalidosException;
import com.sunland.hotel.exceptions.DadosNaoInformadosException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

/**
 * Representa uma reserva de quarto no hotel por intervalo de datas,
 * com snapshot financeiro e máquina de estados estrita.
 */
public class Reserva {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final UUID id;
    private final Hospede hospede;
    private final Quarto quarto;
    private final LocalDate dataCheckIn;
    private final LocalDate dataCheckOut;
    private final BigDecimal valorDiariaSnapshot;
    private final BigDecimal valorTotal;
    private EstadoReserva estadoReserva;

    /**
     * Constrói uma reserva no estado {@link EstadoReserva#PENDENTE} congelando o valor da diária.
     *
     * @param hospede hóspede titular
     * @param quarto quarto reservado
     * @param dataCheckIn data de entrada
     * @param dataCheckOut data de saída (posterior ao check-in)
     */
    public Reserva(Hospede hospede, Quarto quarto, LocalDate dataCheckIn, LocalDate dataCheckOut) {
        if (hospede == null) {
            throw new DadosNaoInformadosException("Hóspede deve ser informado.");
        }
        if (quarto == null) {
            throw new DadosNaoInformadosException("Quarto deve ser informado.");
        }
        if (dataCheckIn == null || dataCheckOut == null) {
            throw new DadosNaoInformadosException("Datas de check-in e check-out devem ser informadas.");
        }
        if (!dataCheckOut.isAfter(dataCheckIn)) {
            throw new DadosInvalidosException("A data de check-out deve ser posterior à data de check-in.");
        }
        if (quarto.getStatus() == StatusQuarto.MANUTENCAO) {
            throw new ConflitoReservaException(
                    "Quarto " + quarto.getNumero() + " está em manutenção e não pode ser reservado.");
        }

        this.id = UUID.randomUUID();
        this.hospede = hospede;
        this.quarto = quarto;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.valorDiariaSnapshot = quarto.calcularDiaria().setScale(2, RoundingMode.HALF_UP);
        long diarias = ChronoUnit.DAYS.between(dataCheckIn, dataCheckOut);
        this.valorTotal = this.valorDiariaSnapshot
                .multiply(BigDecimal.valueOf(diarias))
                .setScale(2, RoundingMode.HALF_UP);
        this.estadoReserva = EstadoReserva.PENDENTE;
    }

    public UUID getId() {
        return id;
    }

    public String getCodigoCurto() {
        return id.toString().substring(0, 8).toUpperCase();
    }

    public Hospede getHospede() {
        return hospede;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public LocalDate getDataCheckIn() {
        return dataCheckIn;
    }

    public LocalDate getDataCheckOut() {
        return dataCheckOut;
    }

    public long getQtdeDiarias() {
        return ChronoUnit.DAYS.between(dataCheckIn, dataCheckOut);
    }

    public BigDecimal getValorDiariaSnapshot() {
        return valorDiariaSnapshot;
    }

    public BigDecimal calcularValorTotal() {
        return valorTotal;
    }

    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }

    /**
     * Confirma uma reserva pendente ({@code PENDENTE -> CONFIRMADA}).
     *
     * @throws IllegalStateException se a reserva não estiver pendente
     */
    public void confirmar() {
        if (estadoReserva != EstadoReserva.PENDENTE) {
            throw new IllegalStateException(
                    "Reserva só pode ser confirmada quando está Pendente (estado atual: "
                            + estadoReserva.getDescricao() + ").");
        }
        this.estadoReserva = EstadoReserva.CONFIRMADA;
    }

    /**
     * Realiza o check-in ({@code CONFIRMADA -> ATIVA}) e ocupa o quarto.
     *
     * @throws IllegalStateException se a reserva não estiver confirmada ou o quarto não puder ser ocupado
     */
    public void realizarCheckIn() {
        if (estadoReserva != EstadoReserva.CONFIRMADA) {
            throw new IllegalStateException(
                    "Para realizar check-in, a reserva deve estar Confirmada (estado atual: "
                            + estadoReserva.getDescricao() + ").");
        }
        quarto.ocupar();
        this.estadoReserva = EstadoReserva.ATIVA;
    }

    /**
     * Realiza o check-out ({@code ATIVA -> FINALIZADA}) e libera o quarto.
     *
     * @throws IllegalStateException se a reserva não estiver ativa
     */
    public void realizarCheckOut() {
        if (estadoReserva != EstadoReserva.ATIVA) {
            throw new IllegalStateException(
                    "Para realizar check-out, a reserva deve estar Ativa (estado atual: "
                            + estadoReserva.getDescricao() + ").");
        }
        quarto.liberar();
        this.estadoReserva = EstadoReserva.FINALIZADA;
    }

    /**
     * Cancela uma reserva pendente ou confirmada ({@code PENDENTE|CONFIRMADA -> CANCELADA}).
     *
     * @throws IllegalStateException se a reserva estiver ativa, finalizada ou já cancelada
     */
    public void cancelar() {
        if (estadoReserva != EstadoReserva.PENDENTE && estadoReserva != EstadoReserva.CONFIRMADA) {
            throw new IllegalStateException(
                    "Não é possível cancelar uma reserva no estado " + estadoReserva.getDescricao() + ".");
        }
        this.estadoReserva = EstadoReserva.CANCELADA;
    }

    /**
     * Indica se esta reserva bloqueia a agenda do quarto para novas reservas.
     *
     * @return {@code true} se estiver Confirmada ou Ativa
     */
    public boolean ocupaAgenda() {
        return estadoReserva == EstadoReserva.CONFIRMADA || estadoReserva == EstadoReserva.ATIVA;
    }

    /**
     * Verifica se o período {@code [inicio, fim)} sobrepõe o período desta reserva.
     * Reservas encostadas (check-out de uma no mesmo dia do check-in de outra) não conflitam.
     *
     * @param inicio data de entrada a testar
     * @param fim data de saída a testar
     * @return {@code true} se houver sobreposição de dias
     */
    public boolean conflitaCom(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null) {
            throw new DadosNaoInformadosException("Datas de início e fim devem ser informadas.");
        }
        if (!fim.isAfter(inicio)) {
            throw new DadosInvalidosException("A data de fim deve ser posterior à data de início.");
        }
        return this.dataCheckIn.isBefore(fim) && this.dataCheckOut.isAfter(inicio);
    }

    public String exibirDescricao() {
        return "Reserva [" + getCodigoCurto() + "] | Hóspede: " + hospede.getNome()
                + " (" + hospede.getCpfFormatado() + ")\n"
                + "Quarto nº: " + quarto.getNumero()
                + " | Período: " + dataCheckIn.format(DATE_FORMATTER)
                + " até " + dataCheckOut.format(DATE_FORMATTER)
                + " (" + getQtdeDiarias() + " diárias)\n"
                + "Estado: " + estadoReserva.getDescricao()
                + " | Diária: R$ " + valorDiariaSnapshot
                + " | Total: R$ " + valorTotal + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Reserva other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
