package br.com.biblioteca.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Singleton para EntityManagerFactory.
 */
public class HibernateUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliotecaPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
