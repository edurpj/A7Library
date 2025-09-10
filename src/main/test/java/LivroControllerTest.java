import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LivroControllerTest {

    @Mock
    private LivroService livroService;

    @Test
    void testBuscarLivroPorId() {
        LivroController controller = new LivroController(livroService);
        String idLivro = "123";
        Livro livroEncontrado = new Livro(idLivro, "O Senhor dos Anéis", "J.R.R. Tolkien");

        when(livroService.buscarPorId(idLivro)).thenReturn(livroEncontrado);

        Livro resultado = controller.buscarLivro(idLivro);

        verify(livroService).buscarPorId(idLivro);

        assert(resultado.equals(livroEncontrado));
    }
}

class LivroController {
    private LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    public Livro buscarLivro(String id) {
        return livroService.buscarPorId(id);
    }
}

class LivroService {
    public Livro buscarPorId(String id) {
        return null;
    }
}

class Livro {
    private String id;
    private String titulo;
    private String autor;

    public Livro(String id, String titulo, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
    }
}