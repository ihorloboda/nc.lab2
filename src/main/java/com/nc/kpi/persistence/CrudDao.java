package com.nc.kpi.persistence;

import org.jetbrains.annotations.NotNull;

public interface CrudDao<T> {
    T find(@NotNull Long id);

    void add(@NotNull T entity);

    void update(@NotNull T entity);

    void delete(@NotNull Long id);
}
