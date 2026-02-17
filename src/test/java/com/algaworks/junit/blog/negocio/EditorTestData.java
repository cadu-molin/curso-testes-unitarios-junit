package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;

import java.math.BigDecimal;

public class EditorTestData {

    private EditorTestData() {}

    public static Editor.Builder umEditorNovo() {
        return Editor.builder()
                .nome("Carlos")
                .email("carlos@gmail.com")
                .valorPagoPorPalavra(BigDecimal.TEN)
                .premium(true);
    }

    public static Editor.Builder umEditorExistente() {
        return umEditorNovo()
                .id(1L);
    }

    public static Editor.Builder umEditorInexistente() {
        return umEditorNovo()
                .id(100L);
    }
}
