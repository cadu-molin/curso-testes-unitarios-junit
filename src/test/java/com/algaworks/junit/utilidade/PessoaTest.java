package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PessoaTest {

    @Test
    public void assercao_agrupada_() {
        Pessoa pessoa = new Pessoa("Alex", "Silva");

        assertAll("Asserções de pessoa",
            () -> assertEquals("Alex", pessoa.getNome()),
            () -> assertEquals("Silva", pessoa.getSobrenome())
        );
    }
}