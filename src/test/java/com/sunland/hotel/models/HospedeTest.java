package com.sunland.hotel.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sunland.hotel.exceptions.DadosInvalidosException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class HospedeTest {

    @Test
    void deveCriarHospedeComDadosValidosENormalizarCpfEEmail() {
        Hospede hospede = new Hospede("  Maria Silva  ", "529.982.247-25", "  Maria.Silva@Exemplo.COM  ");

        assertNotNull(hospede.getId());
        assertEquals("Maria Silva", hospede.getNome());
        assertEquals("52998224725", hospede.getCpf());
        assertEquals("529.982.247-25", hospede.getCpfFormatado());
        assertEquals("maria.silva@exemplo.com", hospede.getEmail());
        assertTrue(hospede.toString().contains("529.982.247-25"));
    }

    @Test
    void deveAceitarCpfSemFormatacao() {
        Hospede hospede = new Hospede("João Souza", "11144477735", "joao@hotel.com");

        assertEquals("11144477735", hospede.getCpf());
        assertEquals("111.444.777-35", hospede.getCpfFormatado());
    }

    @Test
    void deveAceitarNomesComAcentosHifenEApostrofoENormalizarEspacos() {
        Hospede h1 = new Hospede("  Maria   Silva  ", "52998224725", "maria@hotel.com");
        Hospede h2 = new Hospede("João D'Ávila", "11144477735", "joao@hotel.com");
        Hospede h3 = new Hospede("Ana-Maria Costa", "52998224725", "ana@hotel.com");

        assertEquals("Maria Silva", h1.getNome());
        assertEquals("João D'Ávila", h2.getNome());
        assertEquals("Ana-Maria Costa", h3.getNome());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
        "   ",
        "1234567890",
        "123456789012",
        "11111111111",
        "00000000000",
        "52998224720",
        "52998224724",
        "529.982.247-00",
        "abc52998224725",
        "529@982#247-25",
        "52.998.224-725",
        "abc.def.ghi-jk"
    })
    void deveRejeitarCpfsInvalidos(String cpfInvalido) {
        assertFalse(Hospede.validaCpf(cpfInvalido));
        assertThrows(DadosInvalidosException.class,
                () -> new Hospede("Ana Clara", cpfInvalido, "ana@exemplo.com"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
        "  ",
        "Jo",
        "A",
        "A B",
        "Maria 123",
        "João @#$",
        "12345",
        "---",
        "Maria--Silva",
        "-Maria",
        "Maria-"
    })
    void deveRejeitarNomesCurtosVaziosOuComNumerosESimbolos(String nomeInvalido) {
        assertFalse(Hospede.validaNome(nomeInvalido));
        assertThrows(DadosInvalidosException.class,
                () -> new Hospede(nomeInvalido, "52998224725", "ana@exemplo.com"));
    }

    @Test
    void deveRejeitarNomeMaiorQue100Caracteres() {
        String nomeLongo = "A".repeat(101);
        assertThrows(DadosInvalidosException.class,
                () -> new Hospede(nomeLongo, "52998224725", "ana@exemplo.com"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
        "a@b",
        "a@@b.com",
        "@exemplo.com",
        "usuario@",
        "sem-arroba.com",
        "a b@c.com",
        ".maria@hotel.com",
        "maria.@hotel.com",
        "maria..silva@hotel.com",
        "maria@-hotel.com",
        "maria@hotel-.com",
        "maria@hotel..com",
        "maria@hotel.123"
    })
    void deveRejeitarEmailsInvalidos(String emailInvalido) {
        assertFalse(Hospede.validaEmail(emailInvalido));
        assertThrows(DadosInvalidosException.class,
                () -> new Hospede("Ana Clara", "52998224725", emailInvalido));
    }

    @Test
    void deveRejeitarEmailMaiorQue254Caracteres() {
        String emailLongo = "a".repeat(245) + "@hotel.com";
        assertFalse(Hospede.validaEmail(emailLongo));
        assertThrows(DadosInvalidosException.class,
                () -> new Hospede("Ana Clara", "52998224725", emailLongo));
    }

    @Test
    void deveConsiderarHospedesIguaisQuandoCpfNormalizadoForIgual() {
        Hospede h1 = new Hospede("Maria Um", "529.982.247-25", "um@hotel.com");
        Hospede h2 = new Hospede("Maria Dois", "52998224725", "dois@hotel.com");
        Hospede h3 = new Hospede("João", "11144477735", "joao@hotel.com");

        assertEquals(h1, h2);
        assertEquals(h1.hashCode(), h2.hashCode());
        assertNotEquals(h1, h3);
    }
}
