package com.nc.kpi.persistence;

import org.jetbrains.annotations.NotNull;

public interface CrudDao<T> {
    void add(@NotNull T entity);

    T find(@NotNull Long id);

    void update(@NotNull T entity);

    void delete(@NotNull Long id);
}
