package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Task;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.TaskDao;
import com.nc.kpi.persistence.metamodel.rows.ObjectRow;
import com.nc.kpi.persistence.metamodel.rows.ParamRow;
import com.nc.kpi.persistence.metamodel.rows.RefRow;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDaoImpl extends AbstractDao<Task> implements TaskDao {
    @Autowired
    public TaskDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected void addObject(Task entity) {

    }

    @Override
    protected void addParams(Task entity) {

    }

    @Override
    protected void addRefs(Task entity) {

    }

    @Override
    protected void updateObject(Task entity) {

    }

    @Override
    protected void updateParams(Task entity) {

    }

    @Override
    protected void updateRefs(Task entity) {

    }

    @Override
    protected void updateVersion(Task entity) {

    }

    @Override
    protected Task mapObject(@Nullable ObjectRow object) {
        return null;
    }

    @Override
    protected void mapParams(@Nullable List<ParamRow> params, Task entity) {

    }

    @Override
    protected void mapRefs(@Nullable List<RefRow> refs, Task entity) {

    }
}
