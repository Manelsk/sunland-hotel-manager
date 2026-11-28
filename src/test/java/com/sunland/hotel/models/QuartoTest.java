package com.sunland.hotel.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sunland.hotel.enums.StatusQuarto;
import com.sunland.hotel.enums.TipoCama;
import com.sunland.hotel.enums.TipoSuite;
import com.sunland.hotel.exceptions.DadosInvalidosException;
import com.sunland.hotel.exceptions.DadosNaoInformadosException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QuartoTest {

    @Test
    void deveCalcularDiariaQuartoSimplesComESemArCondicionado() {
        QuartoSimples semAr = new QuartoSimples(101, new BigDecimal("150.00"), 1, false);
        QuartoSimples comAr = new QuartoSimples(102, new BigDecimal("150.00"), 1, true);

        assertNotNull(semAr.getId());
        assertEquals(new BigDecimal("150.00"), semAr.calcularDiaria());
        assertEquals(new BigDecimal("180.00"), comAr.calcularDiaria());
        assertTrue(comAr.exibirDescricao().contains("Sim"));
    }

    @Test
    void deveCalcularDiariaQuartoDuploComESemVaranda() {
        QuartoDuplo semVaranda = new QuartoDuplo(201, new BigDecimal("250.00"), 2, TipoCama.SOLTEIRO, false);
        QuartoDuplo comVaranda = new QuartoDuplo(202, new BigDecimal("250.00"), 2, TipoCama.CASAL, true);

        assertEquals(new BigDecimal("250.00"), semVaranda.calcularDiaria());
        assertEquals(new BigDecimal("300.00"), comVaranda.calcularDiaria());
        assertEquals(TipoCama.CASAL, comVaranda.getTipoCama());
    }

    @Test
    void deveCalcularDiariaSuiteExecutivaEPresidencial() {
        Suite executiva = new Suite(301, new BigDecimal("400.00"), 3, TipoSuite.EXECUTIVA);
        Suite presidencial = new Suite(302, new BigDecimal("400.00"), 4, TipoSuite.PRESIDENCIAL);

        assertEquals(new BigDecimal("600.00"), executiva.calcularDiaria());
        assertEquals(new BigDecimal("800.00"), presidencial.calcularDiaria());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -50})
    void deveRejeitarNumeroECapacidadeMenoresOuIguaisAZero(int valorInvalido) {
        assertThrows(DadosInvalidosException.class,
                () -> new QuartoSimples(valorInvalido, new BigDecimal("100.00"), 1, false));
        assertThrows(DadosInvalidosException.class,
                () -> new QuartoSimples(101, new BigDecimal("100.00"), valorInvalido, false));
    }

    @Test
    void deveRejeitarPrecoBaseNuloZeroOuNegativo() {
        assertThrows(DadosNaoInformadosException.class,
                () -> new QuartoSimples(101, null, 1, false));
        assertThrows(DadosInvalidosException.class,
                () -> new QuartoSimples(101, BigDecimal.ZERO, 1, false));
        assertThrows(DadosInvalidosException.class,
                () -> new QuartoSimples(101, new BigDecimal("-10.00"), 1, false));
    }

    @Test
    void deveRejeitarTipoCamaOuTipoSuiteNulos() {
        assertThrows(DadosNaoInformadosException.class,
                () -> new QuartoDuplo(201, new BigDecimal("200.00"), 2, null, false));
        assertThrows(DadosNaoInformadosException.class,
                () -> new Suite(301, new BigDecimal("400.00"), 2, null));
    }

    @Test
    void deveConverterEnumsDeFormaCaseInsensitive() {
        assertEquals(TipoCama.CASAL, TipoCama.fromString("  casal "));
        assertEquals(TipoCama.SOLTEIRO, TipoCama.fromString("SOLTEIRO"));
        assertEquals(TipoSuite.EXECUTIVA, TipoSuite.fromString("executiva"));
        assertEquals(TipoSuite.PRESIDENCIAL, TipoSuite.fromString("Presidencial"));

        assertThrows(DadosInvalidosException.class, () -> TipoCama.fromString("King"));
        assertThrows(DadosInvalidosException.class, () -> TipoSuite.fromString("Simples"));
        assertThrows(DadosInvalidosException.class, () -> TipoCama.fromString(" "));
    }

    @Test
    void deveGerenciarCicloDeStatusEManutencaoDoQuarto() {
        Quarto quarto = new QuartoSimples(101, new BigDecimal("120.00"), 1, false);

        assertEquals(StatusQuarto.DISPONIVEL, quarto.getStatus());
        assertTrue(quarto.estaDisponivel());

        quarto.ocupar();
        assertEquals(StatusQuarto.OCUPADO, quarto.getStatus());
        assertFalse(quarto.estaDisponivel());
        assertThrows(IllegalStateException.class, quarto::ocupar);
        assertThrows(IllegalStateException.class, quarto::colocarEmManutencao);

        quarto.liberar();
        assertTrue(quarto.estaDisponivel());
        assertThrows(IllegalStateException.class, quarto::liberar);

        quarto.colocarEmManutencao();
        assertEquals(StatusQuarto.MANUTENCAO, quarto.getStatus());
        assertThrows(IllegalStateException.class, quarto::ocupar);

        quarto.finalizarManutencao();
        assertTrue(quarto.estaDisponivel());
        assertThrows(IllegalStateException.class, quarto::finalizarManutencao);
    }

    @Test
    void deveCompararQuartosPorNumero() {
        Quarto q1 = new QuartoSimples(101, new BigDecimal("100.00"), 1, false);
        Quarto q2 = new QuartoDuplo(101, new BigDecimal("200.00"), 2, TipoCama.CASAL, false);
        Quarto q3 = new QuartoSimples(102, new BigDecimal("100.00"), 1, false);

        assertEquals(q1, q2);
        assertEquals(q1.hashCode(), q2.hashCode());
        assertNotEquals(q1, q3);
    }
}
