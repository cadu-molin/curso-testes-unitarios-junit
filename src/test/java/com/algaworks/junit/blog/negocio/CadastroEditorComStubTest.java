package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CadastroEditorComStubTest {

    CadastroEditor cadastroEditor;
    ArmazenamentoEditorFixoEmMemoria armazenamentoEditorFixoEmMemoria;
    Editor editor;

    @BeforeEach
    void beforeEach() {
        editor = new Editor(null, "Carlos", "carlos@gmail.com", BigDecimal.TEN, true);
        armazenamentoEditorFixoEmMemoria = new ArmazenamentoEditorFixoEmMemoria();

        cadastroEditor = new CadastroEditor(
                armazenamentoEditorFixoEmMemoria,
                new GerenciadorEnvioEmail() {
                    @Override
                    void enviarEmail(Mensagem mensagem) {
                        System.out.println("Enviando mensagem para:" + mensagem.getDestinatario());
                    }
                }
        );
    }

    @Test
    public void Dado_um_editor_valido_Quando_criar_Entao_deve_retornar_um_id_de_cadastro() {
        Editor editorSalvo = cadastroEditor.criar(editor);

        assertEquals(1L, editorSalvo.getId());
        assertTrue(armazenamentoEditorFixoEmMemoria.chamouSalvar);
    }

    @Test
    public void Dado_um_editor_null_Quando_criar_Entao_deve_retornar_exception() {
        assertThrows(NullPointerException.class, () -> cadastroEditor.criar(null));
        assertFalse(armazenamentoEditorFixoEmMemoria.chamouSalvar);
    }

    @Test
    public void Dado_um_editor_com_email_existente_Quando_criar_Entao_deve_lancar_exception() {
        editor.setEmail("carlos.existente@gmail.com");

        assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editor));
    }

    @Test
    public void Dado_um_editor_com_email_existente_Quando_criar_Entao_nao_deve_salvar() {
        editor.setEmail("carlos.existente@gmail.com");

        try{
            cadastroEditor.criar(editor);
        } catch (Exception e) {}

        assertFalse(armazenamentoEditorFixoEmMemoria.chamouSalvar);
    }
}