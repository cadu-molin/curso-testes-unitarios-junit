package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;

import java.math.BigDecimal;

public class PostTestData {

    private PostTestData() {}

    public static Post.Builder umPostNovo() {
        return Post.builder()
                .titulo("Olá mundo Java")
                .conteudo("Olá mundo Java conteudo")
                .autor(EditorTestData.umEditorNovo().build())
                .pago(true)
                .publicado(true);
    }

    public static Post.Builder umPostExistente() {
        return umPostNovo()
                .id(1L)
                .slug("ola-mundo-java")
                .ganhos(new Ganhos(BigDecimal.TEN, 8, BigDecimal.TEN));
    }
}
