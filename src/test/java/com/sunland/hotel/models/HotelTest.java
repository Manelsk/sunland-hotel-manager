package com.sunland.hotel.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sunland.hotel.enums.EstadoReserva;
import com.sunland.hotel.enums.TipoCama;
import com.sunland.hotel.enums.TipoSuite;
import com.sunland.hotel.exceptions.ConflitoReservaException;
import com.sunland.hotel.exceptions.DadosInexistentesException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HotelTest {

    private Hotel hotel;
    private Hospede maria;
    private Hospede joao;
    private Quarto quarto101;
    private Quarto quarto201;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        maria = new Hospede("Maria Silva", "529.982.247-25", "maria@hotel.com");
        joao = new Hospede("João Souza", "11144477735", "joao@hotel.com");
        quarto101 = new QuartoSimples(101, new BigDecimal("150.00"), 1, true);
        quarto201 = new QuartoDuplo(201, new BigDecimal("250.00"), 2, TipoCama.CASAL, true);

        hotel.adicionarHospede(maria);
        hotel.adicionarHospede(joao);
        hotel.adicionarQuarto(quarto101);
        hotel.adicionarQuarto(quarto201);
    }

    @Test
    void deveRejeitarCpfDuplicadoMesmoComFormatacaoDiferente() {
        Hospede duplicado = new Hospede("Maria Clone", "52998224725", "clone@hotel.com");
        assertThrows(ConflitoReservaException.class, () -> hotel.adicionarHospede(duplicado));
    }

    @Test
    void deveRejeitarNumeroDeQuartoDuplicado() {
        Quarto duplicado = new Suite(101, new BigDecimal("500.00"), 2, TipoSuite.PRESIDENCIAL);
        assertThrows(ConflitoReservaException.class, () -> hotel.adicionarQuarto(duplicado));
    }

    @Test
    void deveBuscarHospedeNormalizandoCpfEQuartoPorNumero() {
        assertEquals(maria, hotel.buscarHospedePorCpf("52998224725"));
        assertEquals(maria, hotel.buscarHospedePorCpf("529.982.247-25"));
        assertEquals(quarto101, hotel.buscarQuartoPorNumero(101));

        assertThrows(DadosInexistentesException.class, () -> hotel.buscarHospedePorCpf("99999999999"));
        assertThrows(DadosInexistentesException.class, () -> hotel.buscarQuartoPorNumero(999));
        assertThrows(DadosInexistentesException.class, () -> hotel.buscarReservaPorId(UUID.randomUUID()));
    }

    @Test
    void deveImpedirOverbookingQuandoDuasReservasPendentesCompetemPeloMesmoPeriodo() {
        LocalDate inicio1 = LocalDate.of(2026, 12, 10);
        LocalDate fim1 = LocalDate.of(2026, 12, 15);
        LocalDate inicio2 = LocalDate.of(2026, 12, 12);
        LocalDate fim2 = LocalDate.of(2026, 12, 16);

        Reserva r1 = hotel.criarReserva(maria, quarto101, inicio1, fim1);
        Reserva r2 = hotel.criarReserva(joao, quarto101, inicio2, fim2);

        hotel.confirmarReserva(r1.getId());
        assertEquals(EstadoReserva.CONFIRMADA, r1.getEstadoReserva());

        // Segunda reserva pendente sobreposta não pode ser confirmada
        assertThrows(ConflitoReservaException.class, () -> hotel.confirmarReserva(r2.getId()));
        assertEquals(EstadoReserva.PENDENTE, r2.getEstadoReserva());

        // Nova tentativa de criação também é bloqueada enquanto houver reserva confirmada
        assertThrows(ConflitoReservaException.class,
                () -> hotel.criarReserva(joao, quarto101, inicio2, fim2));
    }

    @Test
    void deveListarQuartosDisponiveisConsiderandoAgendaManutencaoEFronteiraDeDatas() {
        LocalDate inicio = LocalDate.of(2026, 12, 10);
        LocalDate fim = LocalDate.of(2026, 12, 15);

        Reserva confirmada = hotel.criarReserva(maria, quarto101, inicio, fim);
        hotel.confirmarReserva(confirmada.getId());
        quarto201.colocarEmManutencao();

        List<Quarto> disponiveisConflito = hotel.listarQuartosDisponiveis(
                LocalDate.of(2026, 12, 11), LocalDate.of(2026, 12, 13));
        assertTrue(disponiveisConflito.isEmpty());

        // Na data de check-out (dia 15), o quarto 101 volta a ficar disponível
        List<Quarto> disponiveisNaFronteira = hotel.listarQuartosDisponiveis(
                LocalDate.of(2026, 12, 15), LocalDate.of(2026, 12, 18));
        assertEquals(List.of(quarto101), disponiveisNaFronteira);
    }

    @Test
    void devePermitirMultiplasReservasParaOMesmoHospedeEBuscarPorCpfECodigoCurto() {
        Reserva r1 = hotel.criarReserva(maria, quarto101,
                LocalDate.of(2026, 12, 1), LocalDate.of(2026, 12, 3));
        hotel.confirmarReserva(r1.getId());
        hotel.realizarCheckIn(r1.getId());
        hotel.realizarCheckOut(r1.getId());

        Reserva r2 = hotel.criarReserva(maria, quarto101,
                LocalDate.of(2026, 12, 20), LocalDate.of(2026, 12, 22));

        List<Reserva> reservasMaria = hotel.buscarReservasPorCpf("52998224725");
        assertEquals(2, reservasMaria.size());
        assertEquals(r2, hotel.buscarReservaPorCodigoCurto(r2.getCodigoCurto().toLowerCase()));
        assertThrows(DadosInexistentesException.class, () -> hotel.buscarReservaPorCodigoCurto("ZZZZZZZZ"));
    }

    @Test
    void deveProtegerContraExclusaoInseguraDeHospedeQuartoEReserva() {
        Reserva reserva = hotel.criarReserva(maria, quarto101,
                LocalDate.of(2026, 12, 10), LocalDate.of(2026, 12, 12));

        // Pendente bloqueia exclusão de hóspede e quarto
        assertThrows(IllegalStateException.class, () -> hotel.excluirHospede(maria));
        assertThrows(IllegalStateException.class, () -> hotel.excluirQuarto(quarto101));

        hotel.confirmarReserva(reserva.getId());
        assertThrows(IllegalStateException.class, () -> hotel.excluirReserva(reserva));

        hotel.realizarCheckIn(reserva.getId());
        assertThrows(IllegalStateException.class, () -> hotel.excluirReserva(reserva));
        assertThrows(IllegalStateException.class, () -> hotel.excluirQuarto(quarto101));

        // Após check-out, o quarto foi liberado e a reserva finalizada pode ser removida antes de excluir quarto/hóspede
        hotel.realizarCheckOut(reserva.getId());
        assertTrue(quarto101.estaDisponivel());

        hotel.excluirHospede(maria);
        hotel.excluirQuarto(quarto101);
        hotel.excluirReserva(reserva);

        assertFalse(hotel.getHospedes().contains(maria));
        assertFalse(hotel.getQuartos().contains(quarto101));
        assertFalse(hotel.getReservas().contains(reserva));
    }

    @Test
    void devePermitirExcluirReservaCanceladaELiberarExclusaoDeHospede() {
        Reserva reserva = hotel.criarReserva(joao, quarto201,
                LocalDate.of(2026, 12, 10), LocalDate.of(2026, 12, 12));
        hotel.confirmarReserva(reserva.getId());
        hotel.cancelarReserva(reserva.getId());

        hotel.excluirReserva(reserva);
        hotel.excluirHospede(joao);

        assertFalse(hotel.getHospedes().contains(joao));
        assertTrue(hotel.getReservas().isEmpty());
    }

    @Test
    void deveRetornarListasImutaveisNosGetters() {
        assertThrows(UnsupportedOperationException.class, () -> hotel.getHospedes().add(maria));
        assertThrows(UnsupportedOperationException.class, () -> hotel.getQuartos().add(quarto101));
        assertThrows(UnsupportedOperationException.class, () -> hotel.getReservas().clear());
    }
}
