import br.com.biblioteca.model.entity.Livro;
import br.com.biblioteca.util.ImportacaoXMLUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ImportacaoXMLUtilTest {

    @Test
    void testLerArquivoXMLDoResources() throws IOException {

        URL resourceUrl = getClass().getClassLoader().getResource("livrosXML.xml");

        assertNotNull(resourceUrl, "Arquivo de teste XML não encontrado na pasta src/test/resources!");

        String caminhoArquivo = resourceUrl.getFile();

        List<Livro> livros = ImportacaoXMLUtil.lerArquivo(caminhoArquivo);

        assertNotNull(livros);
        assertEquals(3, livros.size());

        Livro primeiroLivro = livros.get(0);
        assertEquals("O Ladrão de Raios teste", primeiroLivro.getTitulo());
        assertEquals("978-85-98078-22-5", primeiroLivro.getIsbn());

        Livro segundoLivro = livros.get(1);
        assertEquals("A Sociedade do Anel", segundoLivro.getTitulo());
        assertEquals("978-85-98078-95-5", segundoLivro.getIsbn());

        assertEquals("O Ladrão de Raios teste", livros.get(0).getTitulo());
        assertEquals("A Sociedade do Anel", livros.get(1).getTitulo());
        assertEquals("O Hobbit teste", livros.get(2).getTitulo());
    }
}