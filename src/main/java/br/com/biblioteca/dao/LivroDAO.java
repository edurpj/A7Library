package br.com.biblioteca.dao;

import br.com.biblioteca.model.entity.Livro;

import java.util.List;

/**
 * Interface para operações de persistência.
 */
public interface LivroDAO {

    /**
     * Salva um novo livro no banco de dados.
     * @param livro livro a ser salvo.
     */
    void salvar(Livro livro);

   /**
     * Atualiza os dados de um livro existente.
     * @param livro livro com os dados atualizados.
     */
    void atualizar(Livro livro);

    /**
     * Exclui um livro do banco de dados.
     * @param id livro a ser excluído.
     */
    void excluir(Long id);

    /**
     * Busca um livro pelo seu ID.
     * @param id O ID do livro para ser buscado.
     * @return O LivroDTO correspondente ou null se não for encontrado.
     */
    Livro buscarPorId(Long id);

    /**
     * Retorna uma lista de todos os livros cadastrados.
     * @return Uma lista de LivroDTO.
     */
    List<Livro> listarTodos();

    /**
     * Pesquisa livros com base em uma informação em vários campos.
     * @param infoLivro O termo de pesquisa.
     * @return Uma lista de LivroDTO que correspondem a informação.
     */
    List<Livro> pesquisar(String infoLivro);

    /**
     * Busca um livro pelo seu ISBN.
     * @param isbn O ISBN do livro para ser buscado.
     * @return O Livro ou null se não for encontrado.
     */
    Livro buscarPorIsbn(String isbn);
}
