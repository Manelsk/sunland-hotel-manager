package com.sunland.hotel.models;

import com.sunland.hotel.enums.EstadoReserva;
import com.sunland.hotel.enums.StatusQuarto;
import com.sunland.hotel.exceptions.ConflitoReservaException;
import com.sunland.hotel.exceptions.DadosInexistentesException;
import com.sunland.hotel.exceptions.DadosInvalidosException;
import com.sunland.hotel.exceptions.DadosNaoInformadosException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Gerencia o catálogo de quartos, cadastro de hóspedes e agenda de reservas do hotel,
 * garantindo unicidade, prevenção de overbooking e integridade nas exclusões.
 */
public class Hotel {

    private final List<Quarto> quartos;
    private final List<Hospede> hospedes;
    private final List<Reserva> reservas;

    public Hotel() {
        this.quartos = new ArrayList<>();
        this.hospedes = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    /**
     * Adiciona um quarto garantindo unicidade do número.
     *
     * @throws DadosNaoInformadosException se {@code quarto} for null
     * @throws ConflitoReservaException se já existir quarto com o mesmo número
     */
    public void adicionarQuarto(Quarto quarto) {
        if (quarto == null) {
            throw new DadosNaoInformadosException("Quarto deve ser informado.");
        }
        for (Quarto existente : quartos) {
            if (existente.getNumero() == quarto.getNumero()) {
                throw new ConflitoReservaException(
                        "Quarto com número " + quarto.getNumero() + " já existe.");
            }
        }
        quartos.add(quarto);
    }

    /**
     * Adiciona um hóspede garantindo unicidade do CPF normalizado e do e-mail.
     *
     * @throws DadosNaoInformadosException se {@code hospede} for null
     * @throws ConflitoReservaException se já existir hóspede com o mesmo CPF ou e-mail
     */
    public void adicionarHospede(Hospede hospede) {
        if (hospede == null) {
            throw new DadosNaoInformadosException("Hóspede deve ser informado.");
        }
        for (Hospede existente : hospedes) {
            if (existente.getCpf().equals(hospede.getCpf())) {
                throw new ConflitoReservaException(
                        "Já existe um hóspede cadastrado com o CPF " + hospede.getCpfFormatado() + ".");
            }
            if (existente.getEmail().equals(hospede.getEmail())) {
                throw new ConflitoReservaException(
                        "Já existe um hóspede cadastrado com o email " + hospede.getEmail() + ".");
            }
        }
        hospedes.add(hospede);
    }

    /**
     * Verifica se o quarto está disponível para o período {@code [checkIn, checkOut)}.
     */
    public boolean isQuartoDisponivel(Quarto quarto, LocalDate checkIn, LocalDate checkOut) {
        if (quarto == null) {
            throw new DadosNaoInformadosException("Quarto deve ser informado.");
        }
        if (checkIn == null || checkOut == null) {
            throw new DadosNaoInformadosException("Datas de check-in e check-out devem ser informadas.");
        }
        if (!checkOut.isAfter(checkIn)) {
            throw new DadosInvalidosException("A data de check-out deve ser posterior à data de check-in.");
        }
        if (quarto.getStatus() == StatusQuarto.MANUTENCAO) {
            return false;
        }
        return !existeConflitoAgenda(quarto, checkIn, checkOut, null);
    }

    private boolean existeConflitoAgenda(Quarto quarto, LocalDate checkIn, LocalDate checkOut, UUID ignorarReservaId) {
        for (Reserva reserva : reservas) {
            if (ignorarReservaId != null && reserva.getId().equals(ignorarReservaId)) {
                continue;
            }
            if (reserva.getQuarto().equals(quarto)
                    && reserva.ocupaAgenda()
                    && reserva.conflitaCom(checkIn, checkOut)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Lista todos os quartos disponíveis para o período informado.
     */
    public List<Quarto> listarQuartosDisponiveis(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            throw new DadosNaoInformadosException("Datas de check-in e check-out devem ser informadas.");
        }
        if (!checkOut.isAfter(checkIn)) {
            throw new DadosInvalidosException("A data de check-out deve ser posterior à data de check-in.");
        }
        List<Quarto> disponiveis = new ArrayList<>();
        for (Quarto quarto : quartos) {
            if (isQuartoDisponivel(quarto, checkIn, checkOut)) {
                disponiveis.add(quarto);
            }
        }
        return Collections.unmodifiableList(disponiveis);
    }

    /**
     * Cria uma nova reserva pendente após validar existência das entidades e disponibilidade no período.
     */
    public Reserva criarReserva(Hospede hospede, Quarto quarto, LocalDate checkIn, LocalDate checkOut) {
        if (hospede == null) {
            throw new DadosNaoInformadosException("Hóspede deve ser informado.");
        }
        if (quarto == null) {
            throw new DadosNaoInformadosException("Quarto deve ser informado.");
        }
        if (!hospedes.contains(hospede)) {
            throw new DadosInexistentesException("Hóspede informado não está cadastrado no hotel.");
        }
        if (!quartos.contains(quarto)) {
            throw new DadosInexistentesException("Quarto informado não está cadastrado no hotel.");
        }
        if (!isQuartoDisponivel(quarto, checkIn, checkOut)) {
            throw new ConflitoReservaException(
                    "Quarto " + quarto.getNumero() + " indisponível para o período selecionado.");
        }

        Reserva reserva = new Reserva(hospede, quarto, checkIn, checkOut);
        reservas.add(reserva);
        return reserva;
    }

    /**
     * Confirma uma reserva pendente revalidando conflitos de agenda para evitar overbooking.
     */
    public void confirmarReserva(UUID reservaId) {
        Reserva reserva = buscarReservaPorId(reservaId);
        if (reserva.getQuarto().getStatus() == StatusQuarto.MANUTENCAO) {
            throw new ConflitoReservaException(
                    "Quarto " + reserva.getQuarto().getNumero() + " está em manutenção.");
        }
        if (existeConflitoAgenda(
                reserva.getQuarto(),
                reserva.getDataCheckIn(),
                reserva.getDataCheckOut(),
                reserva.getId())) {
            throw new ConflitoReservaException(
                    "Conflito de agenda: o quarto " + reserva.getQuarto().getNumero()
                            + " já possui reserva confirmada ou ativa neste período.");
        }
        reserva.confirmar();
    }

    public void realizarCheckIn(UUID reservaId) {
        buscarReservaPorId(reservaId).realizarCheckIn();
    }

    public void realizarCheckOut(UUID reservaId) {
        buscarReservaPorId(reservaId).realizarCheckOut();
    }

    public void cancelarReserva(UUID reservaId) {
        buscarReservaPorId(reservaId).cancelar();
    }

    public Quarto buscarQuartoPorNumero(int numero) {
        for (Quarto quarto : quartos) {
            if (quarto.getNumero() == numero) {
                return quarto;
            }
        }
        throw new DadosInexistentesException("Quarto com número " + numero + " não encontrado.");
    }

    public Hospede buscarHospedePorCpf(String cpf) {
        String cpfNormalizado = Hospede.normalizarCpf(cpf);
        for (Hospede hospede : hospedes) {
            if (hospede.getCpf().equals(cpfNormalizado)) {
                return hospede;
            }
        }
        throw new DadosInexistentesException("Hóspede com CPF " + cpf + " não encontrado.");
    }

    public Reserva buscarReservaPorId(UUID id) {
        if (id == null) {
            throw new DadosNaoInformadosException("ID da reserva deve ser informado.");
        }
        for (Reserva reserva : reservas) {
            if (reserva.getId().equals(id)) {
                return reserva;
            }
        }
        throw new DadosInexistentesException("Reserva com ID " + id + " não encontrada.");
    }

    /**
     * Busca uma reserva pelo código curto de 8 caracteres exibido na CLI.
     */
    public Reserva buscarReservaPorCodigoCurto(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new DadosNaoInformadosException("Código da reserva deve ser informado.");
        }
        String normalizado = codigo.trim().toUpperCase();
        for (Reserva reserva : reservas) {
            if (reserva.getCodigoCurto().equals(normalizado)) {
                return reserva;
            }
        }
        throw new DadosInexistentesException("Reserva com código " + normalizado + " não encontrada.");
    }

    /**
     * Retorna todas as reservas associadas ao CPF informado (normalizado).
     */
    public List<Reserva> buscarReservasPorCpf(String cpf) {
        String cpfNormalizado = Hospede.normalizarCpf(cpf);
        List<Reserva> encontradas = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getHospede().getCpf().equals(cpfNormalizado)) {
                encontradas.add(reserva);
            }
        }
        return Collections.unmodifiableList(encontradas);
    }

    /**
     * Exclui um hóspede caso não possua reservas em aberto (Pendente, Confirmada ou Ativa).
     */
    public void excluirHospede(Hospede hospede) {
        if (hospede == null) {
            throw new DadosNaoInformadosException("Hóspede deve ser informado.");
        }
        if (!hospedes.contains(hospede)) {
            throw new DadosInexistentesException("Hóspede informado não existe.");
        }
        for (Reserva reserva : reservas) {
            if (reserva.getHospede().equals(hospede) && possuiVinculoAberto(reserva)) {
                throw new IllegalStateException(
                        "Hóspede não pode ser excluído pois possui reservas pendentes, confirmadas ou ativas.");
            }
        }
        hospedes.remove(hospede);
    }

    /**
     * Exclui uma reserva apenas se ela não estiver Confirmada nem Ativa.
     */
    public void excluirReserva(Reserva reserva) {
        if (reserva == null) {
            throw new DadosNaoInformadosException("Reserva deve ser informada.");
        }
        if (!reservas.contains(reserva)) {
            throw new DadosInexistentesException("Reserva informada não existe.");
        }
        if (reserva.ocupaAgenda()) {
            throw new IllegalStateException(
                    "Reserva no estado " + reserva.getEstadoReserva().getDescricao()
                            + " não pode ser excluída; realize cancelamento ou check-out antes.");
        }
        reservas.remove(reserva);
    }

    /**
     * Exclui um quarto caso não esteja ocupado e não possua reservas em aberto.
     */
    public void excluirQuarto(Quarto quarto) {
        if (quarto == null) {
            throw new DadosNaoInformadosException("Quarto deve ser informado.");
        }
        if (!quartos.contains(quarto)) {
            throw new DadosInexistentesException("Quarto informado não existe.");
        }
        if (quarto.getStatus() == StatusQuarto.OCUPADO) {
            throw new IllegalStateException("Quarto não pode ser excluído pois está ocupado.");
        }
        for (Reserva reserva : reservas) {
            if (reserva.getQuarto().equals(quarto) && possuiVinculoAberto(reserva)) {
                throw new IllegalStateException(
                        "Quarto não pode ser excluído pois possui reservas pendentes, confirmadas ou ativas.");
            }
        }
        quartos.remove(quarto);
    }

    private boolean possuiVinculoAberto(Reserva reserva) {
        EstadoReserva estado = reserva.getEstadoReserva();
        return estado == EstadoReserva.PENDENTE
                || estado == EstadoReserva.CONFIRMADA
                || estado == EstadoReserva.ATIVA;
    }

    public List<Hospede> getHospedes() {
        return Collections.unmodifiableList(hospedes);
    }

    public List<Reserva> getReservas() {
        return Collections.unmodifiableList(reservas);
    }

    public List<Quarto> getQuartos() {
        return Collections.unmodifiableList(quartos);
    }
}
