package br.com.biblioteca.service.impl;

import br.com.biblioteca.controller.LivroController;
import br.com.biblioteca.dao.impl.LivroDAOImpl;
import br.com.biblioteca.model.entity.Livro;
import br.com.biblioteca.service.LivroService;
import br.com.biblioteca.util.ImportacaoCSVUtil;
import br.com.biblioteca.util.ImportacaoXMLUtil;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Service para lógica de negócio.
 */
public class LivroServiceImpl implements LivroService {
    private LivroDAOImpl livroDAOImpl;
    private EntityManager entityManager;

    public LivroServiceImpl(LivroDAOImpl livroDAOImpl, EntityManager entityManager) {
        this.livroDAOImpl = livroDAOImpl;
        this.entityManager = entityManager;
    }

    public void salvarOuAtualizar(Livro livro) {

        Livro livroExistente = livroDAOImpl.buscarPorIsbn(livro.getIsbn());

        if (livroExistente != null && !livroExistente.getId().equals(livro.getId())) {
            throw new IllegalArgumentException("Erro: Já existe um livro cadastrado com este ISBN.");
        }

        if (livro.getId() == null) {
            livroDAOImpl.salvar(livro);
        } else {
            livroDAOImpl.atualizar(livro);
        }
    }

    public Livro buscarLivroPorId(Long id) {
        try {
            return entityManager.find(Livro.class, id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar livro por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void importarLivros(String caminhoArquivo) throws Exception {

        List<Livro> livrosImportados;

        if (caminhoArquivo.toLowerCase().endsWith(".csv")) {
            livrosImportados = ImportacaoCSVUtil.lerArquivo(caminhoArquivo);
        } else if (caminhoArquivo.toLowerCase().endsWith(".xml")) {
            livrosImportados = ImportacaoXMLUtil.lerArquivo(caminhoArquivo);
        } else {
            throw new IllegalArgumentException("Formato de arquivo não suportado. Por favor, selecione um arquivo .csv ou .xml.");
        }

        for (Livro livroDoArquivo : livrosImportados) {
            Livro livroExistente = livroDAOImpl.buscarPorIsbn(livroDoArquivo.getIsbn());

            if (livroExistente != null) {
                livroDoArquivo.setId(livroExistente.getId());
            }

            salvarOuAtualizar(livroDoArquivo);
        }
    }


    public void excluir(Long id) {
        livroDAOImpl.excluir(id);
    }

    public List<Livro> listarTodos() {
        return livroDAOImpl.listarTodos();
    }

    public List<Livro> pesquisar(String infoLivro) {
        return livroDAOImpl.pesquisar(infoLivro);
    }
}
