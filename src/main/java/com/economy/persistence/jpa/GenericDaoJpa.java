package com.economy.persistence.jpa;

import java.util.List;
import java.util.Optional;

import com.economy.persistence.dao.GenericDao;
import com.economy.persistence.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * Implementación base JPA de un DAO genérico con RESOURCE_LOCAL.
 */
public abstract class GenericDaoJpa<T, ID> implements GenericDao<T, ID> {

    private final Class<T> entityClass;

    protected GenericDaoJpa(Class<T> entityClass) { this.entityClass = entityClass; }

    protected EntityManager em() { return JpaUtil.getEntityManager(); }

    @Override
    public Optional<T> findById(ID id) {
        try (EntityManager em = em()) {
            return Optional.ofNullable(em.find(entityClass, id));
        }
    }

    @Override
    public List<T> findAll() {
        try (EntityManager em = em()) {
            return em.createQuery("from " + entityClass.getSimpleName(), entityClass)
                    .getResultList();
        }
    }

    @Override
    public T save(T entity) {
        EntityTransaction tx = null;
        try (EntityManager em = em()) {
            tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
            return entity;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public T update(T entity) {
        EntityTransaction tx = null;
        try (EntityManager em = em()) {
            tx = em.getTransaction();
            tx.begin();
            T merged = em.merge(entity);
            tx.commit();
            return merged;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public void delete(T entity) {
        EntityTransaction tx = null;
        try (EntityManager em = em()) {
            tx = em.getTransaction();
            tx.begin();
            T attached = entity;
            if (!em.contains(entity)) attached = em.merge(entity);
            em.remove(attached);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public void deleteById(ID id) {
        EntityTransaction tx = null;
        try (EntityManager em = em()) {
            tx = em.getTransaction();
            tx.begin();
            T ref = em.find(entityClass, id);
            if (ref != null) em.remove(ref);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public long count() {
        try (EntityManager em = em()) {
            return em.createQuery("select count(e) from " + entityClass.getSimpleName() + " e", Long.class)
                    .getSingleResult();
        }
    }
}
