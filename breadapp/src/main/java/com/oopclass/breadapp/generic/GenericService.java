package com.oopclass.breadapp.generic;

import java.util.List;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 * @param <T>
 */

public interface GenericService<T extends Object> {

    /**
     *
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     *
     * @param entity
     * @return
     */
    T update(T entity);

    /**
     *
     * @param entity
     */
    void delete(T entity);

    /**
     *
     * @param id
     */
    void delete(Long id);

    /**
     *
     * @param entities
     */
    void deleteInBatch(List<T> entities);

    /**
     *
     * @param id
     * @return
     */
    T find(Long id);

    /**
     *
     * @return
     */
    List<T> findAll();
}
