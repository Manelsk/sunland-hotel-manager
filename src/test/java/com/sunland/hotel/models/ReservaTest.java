package com.sunland.hotel.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sunland.hotel.enums.EstadoReserva;
import com.sunland.hotel.enums.StatusQuarto;
import com.sunland.hotel.enums.TipoSuite;
import com.sunland.hotel.exceptions.ConflitoReservaException;
import com.sunland.hotel.exceptions.DadosInvalidosException;
import com.sunland.hotel.exceptions.DadosNaoInformadosException;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservaTest {

    private Hospede hospede;
    private Quarto quarto;
    private LocalDate checkIn;
    private LocalDate checkOut;

    @BeforeEach
    void setUp() {
        hospede = new Hospede("Carlos Mendes", "52998224725", "carlos@hotel.com");
        quarto = new Suite(301, new BigDecimal("300.00"), 2, TipoSuite.EXECUTIVA); // 450.00/dia
        checkIn = LocalDate.of(2026, 12, 10);
        checkOut = LocalDate.of(2026, 12, 14); // 4 diárias
    }

    @Test
    void deveCriarReservaPendenteComSnapshotDePrecoECalculoDeDiarias() {
        Reserva reserva = new Reserva(hospede, quarto, checkIn, checkOut);

        assertNotNull(reserva.getId());
        assertEquals(8, reserva.getCodigoCurto().length());
        assertEquals(EstadoReserva.PENDENTE, reserva.getEstadoReserva());
        assertEquals(4, reserva.getQtdeDiarias());
        assertEquals(new BigDecimal("450.00"), reserva.getValorDiariaSnapshot());
        assertEquals(new BigDecimal("1800.00"), reserva.calcularValorTotal());
        assertFalse(reserva.ocupaAgenda());
    }

    @Test
    void deveManterSnapshotFinanceiroImutavelQuandoPrecoDoQuartoMudar() {
        Reserva reserva = new Reserva(hospede, quarto, checkIn, checkOut);

        quarto.setPrecoBase(new BigDecimal("500.00"));

        assertEquals(new BigDecimal("450.00"), reserva.getValorDiariaSnapshot());
        assertEquals(new BigDecimal("1800.00"), reserva.calcularValorTotal());
    }

    @Test
    void deveRejeitarParametrosNulosDatasInvalidasEQuartoEmManutencao() {
        assertThrows(DadosNaoInformadosException.class,
                () -> new Reserva(null, quarto, checkIn, checkOut));
        assertThrows(DadosNaoInformadosException.class,
                () -> new Reserva(hospede, null, checkIn, checkOut));
        assertThrows(DadosNaoInformadosException.class,
                () -> new Reserva(hospede, quarto, null, checkOut));
        assertThrows(DadosNaoInformadosException.class,
                () -> new Reserva(hospede, quarto, checkIn, null));

        assertThrows(DadosInvalidosException.class,
                () -> new Reserva(hospede, quarto, checkIn, checkIn));
        assertThrows(DadosInvalidosException.class,
                () -> new Reserva(hospede, quarto, checkOut, checkIn));

        quarto.colocarEmManutencao();
        assertThrows(ConflitoReservaException.class,
                () -> new Reserva(hospede, quarto, checkIn, checkOut));
    }

    @Test
    void deveExecutarCicloCompletoDaReservaEAcompanhamentoDoQuarto() {
        Reserva reserva = new Reserva(hospede, quarto, checkIn, checkOut);

        reserva.confirmar();
        assertEquals(EstadoReserva.CONFIRMADA, reserva.getEstadoReserva());
        assertTrue(reserva.ocupaAgenda());
        assertEquals(StatusQuarto.DISPONIVEL, quarto.getStatus());

        reserva.realizarCheckIn();
        assertEquals(EstadoReserva.ATIVA, reserva.getEstadoReserva());
        assertTrue(reserva.ocupaAgenda());
        assertEquals(StatusQuarto.OCUPADO, quarto.getStatus());

        reserva.realizarCheckOut();
        assertEquals(EstadoReserva.FINALIZADA, reserva.getEstadoReserva());
        assertFalse(reserva.ocupaAgenda());
        assertEquals(StatusQuarto.DISPONIVEL, quarto.getStatus());
    }

    @Test
    void devePermitirCancelarQuandoPendenteOuConfirmada() {
        Reserva pendente = new Reserva(hospede, quarto, checkIn, checkOut);
        pendente.cancelar();
        assertEquals(EstadoReserva.CANCELADA, pendente.getEstadoReserva());

        Reserva confirmada = new Reserva(hospede, quarto, checkIn, checkOut);
        confirmada.confirmar();
        confirmada.cancelar();
        assertEquals(EstadoReserva.CANCELADA, confirmada.getEstadoReserva());
        assertFalse(confirmada.ocupaAgenda());
    }

    @Test
    void deveBloquearTransicoesDeEstadoInvalidas() {
        Reserva reserva = new Reserva(hospede, quarto, checkIn, checkOut);

        assertThrows(IllegalStateException.class, reserva::realizarCheckIn);
        assertThrows(IllegalStateException.class, reserva::realizarCheckOut);

        reserva.confirmar();
        assertThrows(IllegalStateException.class, reserva::confirmar);
        assertThrows(IllegalStateException.class, reserva::realizarCheckOut);

        reserva.realizarCheckIn();
        assertThrows(IllegalStateException.class, reserva::confirmar);
        assertThrows(IllegalStateException.class, reserva::realizarCheckIn);
        assertThrows(IllegalStateException.class, reserva::cancelar);

        reserva.realizarCheckOut();
        assertThrows(IllegalStateException.class, reserva::realizarCheckOut);
        assertThrows(IllegalStateException.class, reserva::cancelar);
    }

    @Test
    void deveDetectarSobreposicaoDeDatasERespeitarFronteirasNoMesmoDia() {
        Reserva reserva = new Reserva(hospede, quarto,
                LocalDate.of(2026, 12, 10),
                LocalDate.of(2026, 12, 15));

        assertTrue(reserva.conflitaCom(LocalDate.of(2026, 12, 10), LocalDate.of(2026, 12, 15)));
        assertTrue(reserva.conflitaCom(LocalDate.of(2026, 12, 8), LocalDate.of(2026, 12, 11)));
        assertTrue(reserva.conflitaCom(LocalDate.of(2026, 12, 14), LocalDate.of(2026, 12, 18)));
        assertTrue(reserva.conflitaCom(LocalDate.of(2026, 12, 11), LocalDate.of(2026, 12, 13)));
        assertTrue(reserva.conflitaCom(LocalDate.of(2026, 12, 5), LocalDate.of(2026, 12, 20)));

        // Check-out no dia 10 e novo check-in no dia 10 (e vice-versa no dia 15) não conflitam
        assertFalse(reserva.conflitaCom(LocalDate.of(2026, 12, 5), LocalDate.of(2026, 12, 10)));
        assertFalse(reserva.conflitaCom(LocalDate.of(2026, 12, 15), LocalDate.of(2026, 12, 20)));
    }
}
