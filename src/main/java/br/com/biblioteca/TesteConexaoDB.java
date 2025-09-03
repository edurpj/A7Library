package br.com.biblioteca;

import br.com.biblioteca.util.JPAUtil;

import javax.persistence.EntityManager;

public class TesteConexaoDB {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        System.out.println("✅ Conectado ao PostgreSQL!");
        em.close();
    }
}
