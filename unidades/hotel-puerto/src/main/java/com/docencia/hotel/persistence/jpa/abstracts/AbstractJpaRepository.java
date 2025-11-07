package com.docencia.hotel.persistence.jpa.abstracts;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

import com.docencia.hotel.persistence.interfaces.ICrudRepository;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

public abstract class AbstractJpaRepository<T, ID> implements ICrudRepository<T, ID> {

    private final Class<T> repository;
    
    public AbstractJpaRepository(Class<T> repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsById(ID id) {
        return false;
    }

    @Override
    public T findById(ID id) {
        return null;
    }

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    @Transactional
    public T save(T entity) {
        return null;
    }

    @Override
    @Transactional
    public boolean deleteById(ID id) {
        if (!existsById(id)) {
            return false;
        }
        return true;
    }
}