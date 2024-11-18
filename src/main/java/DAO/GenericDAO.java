package DAO;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.HibernateException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import utils.Util;

public class GenericDAO<T, ID extends Serializable> implements IGenericDAO<T, ID> {

    EntityManagerFactory factory;

    public GenericDAO() {
        this.factory = Util.getCurrentEntityManager();
    }

    @Override
    public void update(T entity) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();

        } catch (HibernateException e) {
            e.printStackTrace();
            if (entityManager != null && entityManager.getTransaction() != null) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                entityManager.getTransaction().rollback();
            }
            entityManager.close();
            e.printStackTrace();

        }

    }

    @Override
    public void create(T entity) {
        System.out.println("Existeix: " + factory.isOpen());
        EntityManager entityManager = factory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            if (entityManager != null && entityManager.getTransaction() != null) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();

        }
        entityManager.close();

    }

    @Override
    public T get(ID id) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            T entity = (T) entityManager.find(getEntityClass(), id);
            entityManager.getTransaction().commit();
            return entity;
        }catch (HibernateException e) {
            e.printStackTrace();
            if (entityManager != null && entityManager.getTransaction() != null) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();


        }
        entityManager.close();
        return null;
    }

    @Override
    public void delete(ID id) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            T entity = (T) entityManager.find(getEntityClass(), id);
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            if (entityManager != null && entityManager.getTransaction() != null) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();

        }
        entityManager.close();

    }

    @Override
    public List<T> findAll() {
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("SELECT e from " + getEntityClass().getName() + " e" );
            List<T> entities =query.getResultList() ;
            entityManager.getTransaction().commit();
            return entities;
        }catch (HibernateException e) {
            e.printStackTrace();
            if (entityManager != null && entityManager.getTransaction() != null) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();

        }
        entityManager.close();
        return null;

    }

    @Override
    public Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
