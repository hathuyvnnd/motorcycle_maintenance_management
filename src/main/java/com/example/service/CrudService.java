package com.example.service;

import java.util.List;

public interface CrudService<T, ID> {
    default List<T> findAll() {
        return List.of();
    }

    default T findById(ID id) {
        return null;
    }

    default T create(T entity) {
        return entity;
    }

    default void update(T entity) {

    }

    default void deleteById(ID id) {

    }

    default boolean exitsById(ID id) {
        return false;
    }
}
