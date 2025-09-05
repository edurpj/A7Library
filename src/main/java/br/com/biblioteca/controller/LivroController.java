package br.com.biblioteca.controller;

import br.com.biblioteca.enums.DataEnum;
import br.com.biblioteca.model.entity.Livro;
import br.com.biblioteca.service.impl.LivroServiceImpl;
import br.com.biblioteca.util.ImportacaoCSVUtil;
import br.com.biblioteca.util.OpenLibraryUtil;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import static br.com.biblioteca.Constants.ISBN_INVALIDO;
import static br.com.biblioteca.Constants.OBRIGATORIO;

/**
 * Lógica de negócio para a tela de cadastro de livros.
 */
public class LivroController {
    private static final Logger LOGGER = Logger.getLogger(LivroController.class.getName());
    private LivroServiceImpl livroServiceImpl;

    public LivroController(LivroServiceImpl livroServiceImpl) {
        this.livroServiceImpl = livroServiceImpl;
    }

    /**
     * Busca dados de um livro na OpenLibrary pelo ISBN.
     *
     * @param isbn O ISBN do livro.
     */
    public Livro buscarLivroPorISBN(String isbn) throws Exception {
        JSONObject objLivro = OpenLibraryUtil.buscarLivroPorISBN(isbn);
        if (objLivro == null) {
            return null;
        }

        Livro livro = new Livro();
        livro.setTitulo(objLivro.optString("title", ""));
        livro.setIsbn(isbn);

        if (objLivro.has("publishers")) {
            Object primeiroItem = objLivro.getJSONArray("publishers").get(0);
            if (primeiroItem instanceof JSONObject) {
                JSONObject editoraObj = (JSONObject) primeiroItem;
                livro.setEditora(editoraObj.optString("name", ""));
            } else if (primeiroItem instanceof String) {
                livro.setEditora((String) primeiroItem);
            }
        }

        if (objLivro.has("publish_date")) {
            String dataCompleta = objLivro.getString("publish_date");
            livro.setDataPublicacao(parseDateFromOpenLibrary(dataCompleta));
        }

        if (objLivro.has("authors")) {
            StringBuilder autores = new StringBuilder();
            for (Object a : objLivro.getJSONArray("authors")) {
                JSONObject autorObj = (JSONObject) a;
                if (autorObj.has("key")) {
                    String autorKey = autorObj.optString("key", "");
                    if (!autorKey.isEmpty()) {
                        JSONObject autorData = OpenLibraryUtil.buscarAutorPorChave(autorKey);
                        if (autorData != null && autorData.has("name")) {
                            autores.append(autorData.optString("name", "Nome não encontrado")).append(", ");
                        }
                    }
                }
            }
            if (autores.length() > 0) autores.setLength(autores.length() - 2);
            livro.setAutores(autores.toString());
        }

        return livro;
    }

    /**
     * Metodo para auxiliar na analise da formatação da data retornada pela API.
     */
    private Date parseDateFromOpenLibrary(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
            formatter.setLenient(false);
            return formatter.parse(dateString);
        } catch (java.text.ParseException e) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
                formatter.setLenient(false);
                return formatter.parse(dateString);
            } catch (java.text.ParseException e2) {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    formatter.setLenient(false);
                    return formatter.parse(dateString);
                } catch (java.text.ParseException e3) {
                    String[] parts = dateString.split(" ");
                    if (parts.length >= 2) {
                        int mes = DataEnum.getNumMes(parts[0]);
                        int ano = Integer.parseInt(parts[parts.length - 1]);
                        if (mes != -1) {
                            return new Date(ano - 1900, mes - 1, 1);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Valida e salva os dados do livro.
     *
     * @param livro O objeto Livro a ser salvo.
     */
    public void salvarLivro(Livro livro) throws IllegalArgumentException, ParseException {

        validarCampos(livro);

        livroServiceImpl.salvarOuAtualizar(livro);
        LOGGER.info("Dados salvos com sucesso!");
    }

    private void validarCampos(Livro livro) throws IllegalArgumentException {
        if (livro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'Título' " + OBRIGATORIO);
        }
        if (livro.getAutores().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'Autores' " + OBRIGATORIO);
        }

        //Validação tipo ISBN
        try {
            String isbnSemChar = livro.getIsbn().replace("-", "");

            if (!isbnSemChar.matches("\\d+")) {
                if (!(isbnSemChar.length() == 10 && isbnSemChar.endsWith("X"))) {
                    throw new IllegalArgumentException(ISBN_INVALIDO)
                    ;
                }
            }

            Long.parseLong(isbnSemChar.replace("X", "0"));

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ISBN_INVALIDO);
        }
        if (livro.getDataPublicacao() == null) {
            throw new IllegalArgumentException("O campo 'Data de Publicação' " + OBRIGATORIO);
        }
    }

    public List<Livro> listarTodos() {
        return livroServiceImpl.listarTodos();
    }

    public void excluirLivro(Long id) {
        livroServiceImpl.excluir(id);
    }

    public List<Livro> pesquisarLivros(String info) {
        return livroServiceImpl.pesquisar(info);
    }

    public Livro buscarLivroPorId(Long id) {
        return livroServiceImpl.buscarLivroPorId(id);
    }

    public void importarCSV(String caminho) {
        ImportacaoCSVUtil.importarCSV(caminho, livroServiceImpl);
    }

    public LivroServiceImpl getLivroService() { return this.livroServiceImpl; }

}