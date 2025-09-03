package br.com.biblioteca.service.impl;

import br.com.biblioteca.dao.impl.LivroDAOImpl;
import br.com.biblioteca.model.entity.Livro;
import br.com.biblioteca.service.LivroService;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Service para lógica de negócio.
 */
public class LivroServiceImpl implements LivroService {
    private LivroDAOImpl livroDAOImpl;

    public LivroServiceImpl(EntityManager em) {
        this.livroDAOImpl = new LivroDAOImpl(em);
    }

    public void salvarOuAtualizar(Livro livro) {
        if (livro.getId() == null) {
            livroDAOImpl.salvar(livro);
        } else {
            livroDAOImpl.atualizar(livro);
        }
    }

    public void excluir(Long id) { livroDAOImpl.excluir(id); }

    public List<Livro> listarTodos() {
        return livroDAOImpl.listarTodos();
    }

    public List<Livro> pesquisar(String infoLivro) {
        return livroDAOImpl.pesquisar(infoLivro);
    }
}
