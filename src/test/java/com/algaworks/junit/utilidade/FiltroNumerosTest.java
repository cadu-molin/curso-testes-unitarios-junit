package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Implementado o BDD — Behavior-Driven Development
// Given ... When ... Then ...
// Dado ... Quando ... Então ...

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FiltroNumerosTest {

    @Test
    public void Dado_uma_lista_de_numeros_Quando_filtrar_por_pares_Entao_deve_retornar_apenas_numeros_pares() {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4);
        List<Integer> numerosPares = Arrays.asList(2, 4);
        List<Integer> resultadoFiltro = FiltroNumeros.numerosPares(numeros);

        assertIterableEquals(numerosPares, resultadoFiltro);
    }

    @Test
    public void Dado_uma_lista_de_numeros_Quando_filtrar_por_impares_Entao_deve_retornar_apenas_numeros_impares() {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4);
        List<Integer> numerosPares = Arrays.asList(1, 3);
        List<Integer> resultadoFiltro = FiltroNumeros.numerosImpares(numeros);

        assertIterableEquals(numerosPares, resultadoFiltro);
    }
}