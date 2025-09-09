import br.com.biblioteca.model.entity.Livro;
import br.com.biblioteca.util.ImportacaoCSVUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ImportacaoCSVUtilTest {

    @Test
    void testLerArquivoDoResources() throws Exception {
        URL resourceUrl = getClass().getClassLoader().getResource("livros.csv");

        assertNotNull(resourceUrl, "Arquivo de teste não encontrado na pasta src/test/resources!");

        String caminhoArquivo = resourceUrl.getFile();

        List<Livro> livros = ImportacaoCSVUtil.lerArquivo(caminhoArquivo);

        assertNotNull(livros);
        assertEquals(8, livros.size());

        Livro primeiroLivro = livros.get(0);
        assertEquals("A Revolução dos Bichos", livros.get(0).getTitulo());
        assertEquals("George Orwell", livros.get(1).getAutores());
        assertEquals("O Pequeno Príncipe", livros.get(3).getTitulo());
        assertEquals("Teste34", livros.get(7).getTitulo());

        assertEquals("978-85-359-0112-9", livros.get(0).getIsbn());
        assertEquals("Companhia das Letras", livros.get(0).getEditora());
    }
}