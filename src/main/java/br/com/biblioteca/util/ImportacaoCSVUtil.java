package br.com.biblioteca.util;

import com.opencsv.CSVReader;
import br.com.biblioteca.model.entity.Livro;
import br.com.biblioteca.service.impl.LivroServiceImpl;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Metodo responsável por importar livros de um arquivo CSV.
 * Colunas: Título, Autores, Data de Publicação, ISBN, Editora
 */
public class ImportacaoCSVUtil {
    public static void importarCSV(String caminhoArquivo, LivroServiceImpl livroServiceImpl) {
        try (CSVReader reader = new CSVReader(new FileReader(caminhoArquivo))) {
            String[] linha;
            reader.readNext();
            while ((linha = reader.readNext()) != null) {
                String titulo = linha[0];
                String autores = linha[1];
                Date dataPublicacao = null;
                if (!linha[2].isEmpty()) {
                    try {
                        dataPublicacao = new SimpleDateFormat("dd/MM/yyyy").parse(linha[2]);
                    } catch (Exception e) { }
                }
                String isbn = linha[3];
                String editora = linha[4];

                Livro livroExistente = livroServiceImpl.pesquisar(isbn).stream()
                        .filter(l -> l.getIsbn().equals(isbn)).findFirst().orElse(null);
                if (livroExistente != null) {
                    livroExistente.setTitulo(titulo);
                    livroExistente.setAutores(autores);
                    livroExistente.setDataPublicacao(dataPublicacao);
                    livroExistente.setEditora(editora);
                    livroServiceImpl.salvarOuAtualizar(livroExistente);
                } else {
                    Livro novoLivro = new Livro();
                    novoLivro.setTitulo(titulo);
                    novoLivro.setAutores(autores);
                    novoLivro.setDataPublicacao(dataPublicacao);
                    novoLivro.setIsbn(isbn);
                    novoLivro.setEditora(editora);
                    livroServiceImpl.salvarOuAtualizar(novoLivro);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
