package com.economy.persistence.dao;

import java.util.List;
import java.util.Optional;

/**
 * DAO genérico con operaciones CRUD básicas.
 */
public interface GenericDao<T, ID> {

    Optional<T> findById(ID id);

    List<T> findAll();

    T create(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteById(ID id);

    long count();
}

