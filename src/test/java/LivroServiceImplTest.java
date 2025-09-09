import br.com.biblioteca.dao.impl.LivroDAOImpl;
import br.com.biblioteca.model.entity.Livro;
import br.com.biblioteca.service.impl.LivroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityManager;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LivroServiceImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private LivroDAOImpl livroDAOImpl;

    @InjectMocks
    private LivroServiceImpl livroService;

    private Livro livroTeste;

    @BeforeEach
    void setUp() {
        livroTeste = new Livro();
        livroTeste.setTitulo("Teste de Livro");
        livroTeste.setAutores("Autor de Teste");
        livroTeste.setIsbn("1234567890123");
    }

    @Test
    void testSalvarNovoLivro() {
        when(livroDAOImpl.buscarPorIsbn(livroTeste.getIsbn())).thenReturn(null);
        livroService.salvarOuAtualizar(livroTeste);
        verify(livroDAOImpl, times(1)).salvar(livroTeste);
        verify(livroDAOImpl, never()).atualizar(any());
    }

    @Test
    void testAtualizarLivroExistente() {
        Livro livroNoBanco = new Livro();
        livroNoBanco.setId(1L);
        livroNoBanco.setIsbn("1234567890123");
        livroNoBanco.setTitulo("Título Antigo");

        Livro livroComNovosDados = new Livro();
        livroComNovosDados.setId(1L);
        livroComNovosDados.setIsbn("1234567890123");
        livroComNovosDados.setTitulo("Título Atualizado");

        when(livroDAOImpl.buscarPorIsbn(livroComNovosDados.getIsbn())).thenReturn(livroNoBanco);

        livroService.salvarOuAtualizar(livroComNovosDados);

        verify(livroDAOImpl, times(1)).atualizar(livroComNovosDados);
        verify(livroDAOImpl, never()).salvar(any());
    }

    @Test
    void testExcluirLivro() {
        livroService.excluir(1L);
        verify(livroDAOImpl, times(1)).excluir(1L);
    }
}