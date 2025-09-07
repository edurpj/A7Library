package br.com.biblioteca.service;

import br.com.biblioteca.model.entity.Livro;

import java.util.List;

/**
 * Interface para a lógica de negócio.
 * Define os métodos que o service deve implementar.
 */
public interface LivroService {

    /**
     * Salva um novo livro ou atualiza um existente.
     * @param livro Livro a ser salvo/atualizado.
     */
    void salvarOuAtualizar(Livro livro);

    /**
     * Exclui um livro pelo seu ID.
     * @param id O ID a ser excluído.
     */
    void excluir(Long id);

    /**
     * Retorna uma lista de todos os livros cadastrados.
     * @return Uma lista de objetos Livro.
     */
    List<Livro> listarTodos();

    /**
     * Pesquisa livros com base em uma informação em vários campos.
     * @param infoLivro O termo de pesquisa.
     * @return Uma lista de objetos Livro que correspondem a informação.
     */
    List<Livro> pesquisar(String infoLivro);

    /**
     * Importa livros com base em um arquivo.
     * @param caminhoArquivo caminho do arquivo com os dados do livro.
     */
    void importarLivros(String caminhoArquivo) throws Exception;
}