package br.com.biblioteca.controller;

import br.com.biblioteca.model.entity.Livro;
import br.com.biblioteca.service.impl.LivroServiceImpl;
import br.com.biblioteca.util.ImportacaoCSVUtil;
import br.com.biblioteca.util.OpenLibraryUtil;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Controla a lógica de negócio para a tela de cadastro de livros.
 */
public class LivroController {
    private static final Logger LOGGER = Logger.getLogger(LivroController.class.getName());
    private LivroServiceImpl livroServiceImpl;

    public LivroController(LivroServiceImpl livroServiceImpl) {
        this.livroServiceImpl = livroServiceImpl;
    }

    /**
     * Busca dados de um livro na OpenLibrary pelo ISBN.
     * @param isbn O ISBN do livro.
     * @return Um objeto Livro com os dados preenchidos ou null se não encontrado.
     */
    public Livro buscarLivroPorISBN(String isbn) throws Exception {
        JSONObject obj = OpenLibraryUtil.buscarLivroPorISBN(isbn);
        if (obj == null) {
            return null;
        }

        Livro livro = new Livro();
        livro.setTitulo(obj.optString("title", ""));
        livro.setIsbn(isbn);

        if (obj.has("publishers")) {
            Object primeiroItem = obj.getJSONArray("publishers").get(0);
            if (primeiroItem instanceof JSONObject) {
                JSONObject editoraObj = (JSONObject) primeiroItem;
                livro.setEditora(editoraObj.optString("name", ""));
            } else if (primeiroItem instanceof String) {
                livro.setEditora((String) primeiroItem);
            }
        }

        if (obj.has("publish_date")) {
            String dataCompleta = obj.getString("publish_date");
            if (dataCompleta.length() > 4) {
                livro.setDataPublicacao(new SimpleDateFormat("yyyy").parse(dataCompleta.substring(0, 4)));
            } else {
                livro.setDataPublicacao(new SimpleDateFormat("yyyy").parse(dataCompleta));
            }
        }

        // Processamento dos Autores
        if (obj.has("authors")) {
            StringBuilder autores = new StringBuilder();
            for (Object a : obj.getJSONArray("authors")) {
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
     * Valida e salva os dados do livro.
     * @param livro O objeto Livro a ser salvo.
     */
    public void salvarLivro(Livro livro) throws IllegalArgumentException, ParseException {

        validarCampos(livro);

        // Caso a API retorne somente o ano, é formatado para 01/01 + ano, afim de não ter problema durante a persistencia.
        String dataString = new SimpleDateFormat("dd/MM/yyyy").format(livro.getDataPublicacao());
        if (dataString.length() == 4) {
            livro.setDataPublicacao(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/" + dataString));
        }

        livroServiceImpl.salvarOuAtualizar(livro);
        LOGGER.info("Dados salvos com sucesso!");
    }

    private void validarCampos(Livro livro) throws IllegalArgumentException {
        if (livro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'Título' é obrigatório.");
        }
        if (livro.getAutores().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'Autores' é obrigatório.");
        }
        if (livro.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'ISBN' é obrigatório.");
        }

        String data = new SimpleDateFormat("dd/MM/yyyy").format(livro.getDataPublicacao());
        if (!data.isEmpty()) {
            try {
                if (data.length() == 4) {
                    Integer.parseInt(data);
                } else {
                    new SimpleDateFormat("dd/MM/yyyy").parse(data);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Formato de data inválido. Use AAAA ou dd/MM/yyyy.");
            }
        }
    }

    public List<Livro> listarTodos() {
        return livroServiceImpl.listarTodos();
    }

    public Livro buscarLivroPorId(Long id) {
        return livroServiceImpl.listarTodos().stream().filter(l -> l.getId().equals(id)).findFirst().orElse(null);
    }

    public void excluirLivro(Long id) {
        livroServiceImpl.excluir(id);
    }

    public List<Livro> pesquisarLivros(String info) {
        return livroServiceImpl.pesquisar(info);
    }

    public void importarCSV(String caminho) {
        ImportacaoCSVUtil.importarCSV(caminho, livroServiceImpl);
    }
}