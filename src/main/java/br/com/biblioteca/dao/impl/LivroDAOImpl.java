package br.com.biblioteca.dao.impl;

import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.model.entity.Livro;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO para operações de persistência de dados.
 */
public class LivroDAOImpl implements LivroDAO {
    private final EntityManager em;

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
        TypedQuery<Livro> query = em.createQuery(
                "SELECT l FROM Livro l WHERE l.titulo LIKE :infoLivro OR l.autores LIKE :infoLivro OR l.isbn LIKE :infoLivro OR l.editora LIKE :infoLivro",
                Livro.class);
        query.setParameter("infoLivro", "%" + infoLivro + "%");
        return query.getResultList();
    }
}