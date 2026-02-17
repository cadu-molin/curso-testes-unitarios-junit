package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;

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

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4, 5,6,7,8,9,10,11})
    public void Dado_horario_matinal_Quando_salvar_Entao_deve_retornar_bom_dia(int hora) {
        assertEquals("Bom dia", SaudacaoUtil.saudar(hora));
    }

    @ParameterizedTest
    @ValueSource(ints = {12,13,14,15,16,17})
    public void Dado_horario_tarde_Quando_salvar_Entao_deve_retornar_boa_tarde(int hora) {
        assertEquals("Boa tarde", SaudacaoUtil.saudar(hora));
    }

    @ParameterizedTest
    @ValueSource(ints = {18,19,20,21,22,23})
    public void Dado_horario_noite_Quando_salvar_Entao_deve_retornar_boa_noite(int hora) {
        assertEquals("Boa noite", SaudacaoUtil.saudar(hora));
    }
}