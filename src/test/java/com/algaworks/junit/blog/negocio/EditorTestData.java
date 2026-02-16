package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;

import java.math.BigDecimal;

public class EditorTestData {

    private EditorTestData() {}

    public static Editor umEditorNovo() {
        return new Editor(null, "Carlos", "carlos@gmail.com", BigDecimal.TEN, true);
    }

    public static Editor umEditorExistente() {
        return new Editor(1L, "Carlos", "carlos@gmail.com", BigDecimal.TEN, true);
    }

    public static Editor umEditorInexistente() {
        return new Editor(100L, "Carlos", "carlos@gmail.com", BigDecimal.TEN, true);
    }
}
