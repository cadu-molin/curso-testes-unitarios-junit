package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("Testes no utilitário saudação")
class SaudacaoUtilTest {

    @Test
    @DisplayName("Deve saudar com bom dia")
    public void deveSaudarComBomDia() {
        assertAll(
            () -> assertEquals("Bom dia", SaudacaoUtil.saudar(0), "Saudação incorreta!"),
            () -> assertEquals("Bom dia", SaudacaoUtil.saudar(11), "Saudação incorreta!")
        );
    }

    @Test
    @DisplayName("Deve saudar com boa tarde")
//    @Disabled
//    @DisabledIfEnvironmentVariable(named = "ENV", matches = "PROD")
    public void deveSaudarComBoaTarde() {
//        assumeTrue("PROD".equals(System.getenv("ENV")), "Abortando teste");
        assertAll(
            () -> assertEquals("Boa tarde", SaudacaoUtil.saudar(12), "Saudação incorreta!"),
            () -> assertEquals("Boa tarde", SaudacaoUtil.saudar(17), "Saudação incorreta!")
        );
    }

    @Test
    @DisplayName("Deve saudar com boa noite")
    public void deveSaudarComBoaNoite() {
        assertAll(
            () -> assertEquals("Boa noite", SaudacaoUtil.saudar(18), "Saudação incorreta!"),
            () -> assertEquals("Boa noite", SaudacaoUtil.saudar(23), "Saudação incorreta!")
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando a hora for inválida")
    public void deveLancarExcecaoQuandoHoraForInvalida() {
        assertEquals("Hora inválida", assertThrows(IllegalArgumentException.class, () -> SaudacaoUtil.saudar(-1)).getMessage());
        assertEquals("Hora inválida", assertThrows(IllegalArgumentException.class, () -> SaudacaoUtil.saudar(24)).getMessage());

    }
}