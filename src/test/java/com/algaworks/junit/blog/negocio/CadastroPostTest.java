package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoPost;
import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Notificacao;
import com.algaworks.junit.blog.modelo.Post;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CadastroPostTest {

    @Mock
    ArmazenamentoPost armazenamentoPost;

    @Mock
    CalculadoraGanhos calculadoraGanhos;

    @Mock
    GerenciadorNotificacao gerenciadorNotificacao;

    @InjectMocks
    CadastroPost cadastroPost;

    @Captor
    ArgumentCaptor<Notificacao> notificacaoArgumentCaptor;

    @Spy
    Editor editor = new Editor(1L, "Carlos", "carlos@gmail.com", BigDecimal.TEN, true);

    @Nested
    class Cadastro {

        @Spy
        Post post = new Post("Olá mundo Java", "Olá mundo Java conteudo", editor, true, true);

        @Test
        void Dado_um_post_valido_Quando_cadastrar_Entao_deve_salvar() {
            when(armazenamentoPost.salvar(any(Post.class)))
                    .then(invocation -> {
                        Post postEnviado = invocation.getArgument(0, Post.class);

                        postEnviado.setId(1L);

                        return postEnviado;
                    });

            cadastroPost.criar(post);

            verify(armazenamentoPost, times(1)).salvar(post);
        }

        @Test
        void Dado_um_post_valido_Quando_cadastrar_Entao_deve_retornar_id_valido() {
            when(armazenamentoPost.salvar(any(Post.class)))
                    .then(invocation -> {
                        Post postEnviado = invocation.getArgument(0, Post.class);

                        postEnviado.setId(1L);

                        return postEnviado;
                    });

            Post postSalvo = cadastroPost.criar(post);

            assertEquals(1L, postSalvo.getId());
        }

        @Test
        void Dado_um_post_valido_Quanto_cadastrar_Entao_deve_retornar_post_com_ganhos() {
            when(armazenamentoPost.salvar(any(Post.class)))
                    .then(invocation -> {
                        Post postEnviado = invocation.getArgument(0, Post.class);

                        postEnviado.setId(1L);

                        return postEnviado;
                    });

            when(calculadoraGanhos.calcular(any(Post.class)))
                    .thenReturn(new Ganhos(BigDecimal.TEN, 8, BigDecimal.valueOf(80)));

            Post postSalvo = cadastroPost.criar(post);

            verify(post, times(1)).setGanhos(any(Ganhos.class));
            assertNotNull(postSalvo.getGanhos());
        }

        @Test
        void Dado_um_post_valido_Quanto_cadastrar_Entao_deve_retornar_post_com_slug() {
            when(armazenamentoPost.salvar(any(Post.class)))
                    .then(invocacao -> {
                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        postEnviado.setId(1L);
                        return postEnviado;
                    });

            Post postSalvo = cadastroPost.criar(post);

            verify(post, times(1)).setSlug(anyString());
            assertNotNull(postSalvo.getSlug());
        }

        @Test
        void Dado_um_post_null_Quanto_cadastrar_Entao_deve_lancar_exception_e_nao_deve_savar() {
            assertThrows(NullPointerException.class, ()-> cadastroPost.criar(null));

            verify(armazenamentoPost, never()).salvar(any(Post.class));
        }

        @Test
        void Dado_um_post_valido_Quanto_cadastrar_Entao_deve_calcular_ganhos_antes_de_salvar() {
            when(armazenamentoPost.salvar(any(Post.class)))
                    .then(invocacao -> {
                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        postEnviado.setId(1L);
                        return postEnviado;
                    });

            cadastroPost.criar(post);

            InOrder inOrder = inOrder(calculadoraGanhos, armazenamentoPost);

            inOrder.verify(calculadoraGanhos, times(1)).calcular(post);
            inOrder.verify(armazenamentoPost, times(1)).salvar(post);
        }

        @Test
        public void Dado_um_post_valido_Quanto_cadastrar_Entao_deve_enviar_notificacao_apos_salvar() {
            when(armazenamentoPost.salvar(any(Post.class)))
                    .then(invocacao -> {
                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        postEnviado.setId(1L);
                        return postEnviado;
                    });

            cadastroPost.criar(post);

            InOrder inOrder = inOrder(gerenciadorNotificacao, armazenamentoPost);
            inOrder.verify(armazenamentoPost, times(1)).salvar(post);
            inOrder.verify(gerenciadorNotificacao, times(1)).enviar(any(Notificacao.class));
        }

        @Test
        public void Dado_um_post_valido_Quanto_cadastrar_Entao_deve_gerar_notificacao_com_titulo_do_post() {
            when(armazenamentoPost.salvar(any(Post.class)))
                    .then(invocacao -> {
                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        postEnviado.setId(1L);
                        return postEnviado;
                    });

            cadastroPost.criar(post);

            verify(gerenciadorNotificacao).enviar(notificacaoArgumentCaptor.capture());

            Notificacao notificacao = notificacaoArgumentCaptor.getValue();
            assertEquals("Novo post criado -> " + post.getTitulo(), notificacao.getConteudo());
        }
    }

    @Nested
    class Edicao {

        @Spy
        Post post = new Post(1L, "Olá mundo Java", "Olá mundo Java conteudo", editor, "ola-mundo-java", new Ganhos(BigDecimal.TEN, 8, BigDecimal.TEN), true, true);

        @Test
        public void Dado_um_post_valido_Quando_editar_Entao_deve_salvar() {
            when(armazenamentoPost.salvar(any(Post.class)))
                    .then(invocacao -> invocacao.getArgument(0, Post.class));
            when(armazenamentoPost.encontrarPorId(1L))
                    .thenReturn(Optional.ofNullable(post));

            cadastroPost.editar(post);

            verify(armazenamentoPost, times(1)).salvar(any(Post.class));
        }

        @Test
        public void Dado_um_post_valido_Quando_editar_Entao_deve_retornar_mesmo_id() {
            when(armazenamentoPost.salvar(any(Post.class)))
                    .then(invocacao -> invocacao.getArgument(0, Post.class));
            when(armazenamentoPost.encontrarPorId(1L))
                    .thenReturn(Optional.ofNullable(post));

            Post postSalvo = cadastroPost.editar(post);

            assertEquals(1L, postSalvo.getId());
        }

        @Test
        public void Dado_um_post_pago_Quando_editar_Entao_deve_retornar_post_com_os_mesmos_ganhos_sem_recalcular() {
            post.setConteudo("Conteúdo editado");
            when(armazenamentoPost.salvar(any(Post.class)))
                    .then(invocacao -> invocacao.getArgument(0, Post.class));
            when(armazenamentoPost.encontrarPorId(1L))
                    .thenReturn(Optional.ofNullable(post));

            Post postSalvo = cadastroPost.editar(post);

            verify(post, never()).setGanhos(any(Ganhos.class));
            verify(post, times(1)).isPago();
            assertNotNull(postSalvo.getGanhos());
        }

        @Test
        public void Dado_um_post_nao_pago_Quando_editar_Entao_deve_retornar_recalcular_ganhos_antes_de_salvar() {
            post.setConteudo("Conteúdo editado");
            post.setPago(false);
            Ganhos novoGanho = new Ganhos(BigDecimal.TEN, 2, BigDecimal.valueOf(20));

            when(armazenamentoPost.salvar(any(Post.class))).then(invocacao -> invocacao.getArgument(0, Post.class));
            when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));
            when(calculadoraGanhos.calcular(post)).thenReturn(novoGanho);

            Post postSalvo = cadastroPost.editar(post);

            verify(post, times(1)).setGanhos(novoGanho);
            assertNotNull(postSalvo.getGanhos());
            assertEquals(novoGanho, postSalvo.getGanhos());

            InOrder inOrder = inOrder(calculadoraGanhos, armazenamentoPost);
            inOrder.verify(calculadoraGanhos, times(1)).calcular(post);
            inOrder.verify(armazenamentoPost, times(1)).salvar(post);
        }

        @Test
        public void Dado_um_post_com_titulo_alterado_Quando_editar_Entao_deve_retornar_post_com_a_mesma_slug_sem_alterar() {
            post.setTitulo("Ola Teste");
            when(armazenamentoPost.salvar(any(Post.class))).then(invocacao -> invocacao.getArgument(0, Post.class));
            when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));

            Post postSalvo = cadastroPost.editar(post);

            verify(post, never()).setSlug(anyString());
            assertEquals("ola-mundo-java", postSalvo.getSlug());
        }

        @Test
        public void Dado_um_post_null_Quando_editar_Entao_deve_lancar_exception_e_nao_deve_savar() {
            assertThrows(NullPointerException.class, ()-> cadastroPost.editar(null));
            verify(armazenamentoPost, never()).salvar(any(Post.class));
        }

        @Test
        public void Dado_um_post_valido_Quando_editar_Entao_deve_deve_alterar_post_salvo() {
            Post postAlterado = new Post(1L, "Olá Java", "Olá Java", editor, "ola-mundo-java", new Ganhos(BigDecimal.TEN, 4, BigDecimal.valueOf(10)), true, true);

            when(armazenamentoPost.salvar(any(Post.class)))
                    .then(invocacao -> invocacao.getArgument(0, Post.class));
            when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));

            cadastroPost.editar(postAlterado);

            verify(post).atualizarComDados(postAlterado);

            InOrder inOrder = inOrder(armazenamentoPost, post);
            inOrder.verify(post).atualizarComDados(postAlterado);
            inOrder.verify(armazenamentoPost).salvar(post);
        }
    }
}