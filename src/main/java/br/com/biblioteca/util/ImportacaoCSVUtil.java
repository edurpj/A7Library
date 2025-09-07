package br.com.biblioteca.util;

import br.com.biblioteca.controller.LivroController;
import br.com.biblioteca.dao.impl.LivroDAOImpl;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import br.com.biblioteca.model.entity.Livro;
import com.opencsv.CSVReaderBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Metodo responsável por importar livros de um arquivo CSV.
 * Colunas: Título, Autores, Data de Publicação, ISBN, Editora
 */
public class ImportacaoCSVUtil {

    private static final Logger LOGGER = Logger.getLogger(ImportacaoCSVUtil.class.getName());
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public static List<Livro> lerArquivo(String caminhoArquivo) throws Exception {
        List<Livro> livros = new ArrayList<>();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliotecaPU");
        EntityManager em = emf.createEntityManager();
        LivroDAOImpl livroDAO = new LivroDAOImpl(em);

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(caminhoArquivo))
                .withSkipLines(1)
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] linha;
            while ((linha = reader.readNext()) != null) {
                if (linha.length < 5) {
                    LOGGER.info("Linha inválida ou em branco.");
                    continue;
                }

                String titulo = linha[0];
                String autores = linha[1];
                String dataPublicacaoStr = linha[2];
                String isbn = linha[3];
                String editora = linha[4];

                Date dataPublicacao = null;
                if (dataPublicacaoStr != null && !dataPublicacaoStr.trim().isEmpty()) {
                    dataPublicacao = DATE_FORMAT.parse(dataPublicacaoStr.trim());
                }

                Livro livro = livroDAO.buscarPorIsbn(isbn);

                if (livro == null) {
                    livro = new Livro();
                    livro.setIsbn(isbn);
                }

                livro.setTitulo(titulo);
                livro.setAutores(autores);
                livro.setDataPublicacao(dataPublicacao);
                livro.setEditora(editora);

                if (livro.getId() == null) {
                    livroDAO.salvar(livro);
                    LOGGER.info("Livro: " + livro.getTitulo() + " salvo com sucesso.");

                } else {
                    livroDAO.atualizar(livro);
                    LOGGER.info("Livro: " + livro.getTitulo() + " atualizado com sucesso.");
                }

                livros.add(livro);
            }
        } finally {
            em.close();
            emf.close();
        }

        return livros;
    }
}
