package com.algaworks.junit.blog.utilidade;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class ConversorSlugTest {

    @Test
    void deveConverterJuntoComCodigo() {
        try(MockedStatic<GeradorCodigo> mockedStatic = mockStatic(GeradorCodigo.class)) {
            mockedStatic.when(GeradorCodigo::gerar).thenReturn("12345678");

            String slug = ConversorSlug.converterJuntoComCodigo("ola mundo java");

            assertEquals("ola-mundo-java-12345678", slug);
        }
    }

}