package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
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
public class CadastroEditorComMockTest {

    @Spy
    Editor editor = new Editor(null, "Carlos", "carlos@gmail.com", BigDecimal.TEN, true);

    @Mock
    ArmazenamentoEditor armazenamentoEditor;

    @Mock
    GerenciadorEnvioEmail gerenciadorEnvioEmail;

    @InjectMocks
    CadastroEditor cadastroEditor;

    @Captor
    ArgumentCaptor<Mensagem> mensagemArgumentCaptor;

    @BeforeEach
    void init() {
        when(armazenamentoEditor.salvar(any(Editor.class))).thenAnswer(invocacao -> {
            Editor editorPassado = invocacao.getArgument(0, Editor.class);
            editorPassado.setId(1L);
            return editorPassado;
        });
    }

    @Test
    void Dado_um_editor_valido_Quando_criar_Entao_deve_retornar_um_id_de_cadastro() {
        Editor editorSalvo = cadastroEditor.criar(editor);

        assertEquals(1L, editorSalvo.getId());
    }

    @Test
    void Dado_um_editor_valido_Quando_criar_Entao_deve_salvar_metodo_salvar_do_armazenamento() {
        cadastroEditor.criar(editor);

        verify(armazenamentoEditor, times(1)).salvar(eq(editor));
    }

    @Test
    void Dado_um_editor_valido_Quando_criar_e_lancar_uma_exception_ao_salvar_Entao_nao_deve_enviar_email() {
        when(armazenamentoEditor.salvar(editor)).thenThrow(RuntimeException.class);

        assertAll("Não deve envial e-mail, quando lançar exception do armazenamento",
                () -> {assertThrows(RuntimeException.class, () -> {cadastroEditor.criar(editor);});},
                () -> verify(gerenciadorEnvioEmail, never()).enviarEmail(any()));
    }

    @Test
    void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_com_destino_ao_editor() {
        Editor editorSalvo = cadastroEditor.criar(editor);

        verify(gerenciadorEnvioEmail).enviarEmail(mensagemArgumentCaptor.capture());

        Mensagem mensagem = mensagemArgumentCaptor.getValue();

        assertEquals(editorSalvo.getEmail(), mensagem.getDestinatario());
    }

    @Test
    void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_verificar_o_email() {
        cadastroEditor.criar(editor);

        verify(editor, atLeast(1)).getEmail();
    }

    @Test
    void Dado_um_editor_com_email_existente_Quando_cadastrar_Entao_deve_lancar_exception() {
        when(armazenamentoEditor.encontrarPorEmail("carlos@gmail.com"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(editor));

        Editor editorComEmailExistente = new Editor(null, "Carlos", "carlos@gmail.com", BigDecimal.TEN, true);

        cadastroEditor.criar(editor);

        assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editorComEmailExistente));
    }

    @Test
    void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_apos_salvar() {
        cadastroEditor.criar(editor);

        InOrder inOrder = inOrder(armazenamentoEditor, gerenciadorEnvioEmail);

        inOrder.verify(armazenamentoEditor, times(1)).salvar(editor);
        inOrder.verify(gerenciadorEnvioEmail, times(1)).enviarEmail(any(Mensagem.class));
    }
}
