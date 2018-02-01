package com.nc.kpi.persistence;

public interface CrudDao<T> {
    T find(Long id);

    void add(T entity);

    void update(T entity);

    void delete(Long id);
}
