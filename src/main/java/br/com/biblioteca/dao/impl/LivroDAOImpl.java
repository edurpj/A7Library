package br.com.biblioteca.dao.impl;

import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.model.entity.Livro;
import br.com.biblioteca.ui.LivroCadastroFrame;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO para operações de persistência de dados.
 */
public class LivroDAOImpl implements LivroDAO {
    private final EntityManager em;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LivroDAOImpl.class.getName());


    public LivroDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void salvar(Livro livro) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(livro);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao salvar o livro: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Livro livro) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(livro);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao atualizar o livro: " + e.getMessage(), e);
        }
    }

    @Override
    public void excluir(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Livro livro = em.find(Livro.class, id);
            if (livro != null) {
                em.remove(livro);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao excluir o livro: " + e.getMessage(), e);
        }
    }

    @Override
    public Livro buscarPorId(Long id) {
        return em.find(Livro.class, id);
    }

    @Override
    public List<Livro> listarTodos() {
        TypedQuery<Livro> query = em.createQuery("SELECT l FROM Livro l", Livro.class);
        return query.getResultList();
    }

    @Override
    public List<Livro> pesquisar(String infoLivro) {
        if (infoLivro == null || infoLivro.trim().isEmpty()) {
            // Se o termo de pesquisa estiver vazio, retorna todos os livros
            return em.createQuery("SELECT l FROM Livro l", Livro.class).getResultList();
        }

        TypedQuery<Livro> query = em.createQuery(
                "SELECT l FROM Livro l WHERE "
                        + "LOWER(l.titulo) LIKE LOWER(:infoLivro) OR "
                        + "LOWER(l.autores) LIKE LOWER(:infoLivro) OR "
                        + "LOWER(l.isbn) LIKE LOWER(:infoLivro) OR "
                        + "LOWER(l.editora) LIKE LOWER(:infoLivro)",
                Livro.class);

        query.setParameter("infoLivro", "%" + infoLivro + "%");

        return query.getResultList();
    }

    @Override
    public Livro buscarPorIsbn(String isbn) {
        TypedQuery<Livro> query = em.createQuery(
                "SELECT l FROM Livro l WHERE l.isbn = :isbn", Livro.class);
        query.setParameter("isbn", isbn);

        try {
            return query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }

    }
}