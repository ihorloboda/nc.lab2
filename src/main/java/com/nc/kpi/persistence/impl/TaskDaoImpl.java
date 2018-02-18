package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Qualification;
import com.nc.kpi.entities.Sprint;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskDaoImpl extends AbstractDao<Task> implements TaskDao {
    @Autowired
    public TaskDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected void addObject(Task entity) {
        String sql = loadSqlStatement(SQL_OBJECT_ADD_PATH);
        entity.setId(generateId(TYPE_TASK));
        entity.setVersion(DEFAULT_OBJECT_VERSION);
        executeUpdate(sql, entity.getId(), DEFAULT_OBJECT_VERSION, TYPE_TASK, entity.getName(), entity.getDesc(),
                entity.getSprint().getId());
    }

    @Override
    protected void addParams(Task entity) {
        String sql = loadSqlStatement(SQL_PARAM_ADD_PATH);
        List<Object[]> params = new ArrayList<>(4);
        params.add(new Object[]{entity.getId(), ATTR_ACTIVE, null, null, null, null, entity.getActive()});
        params.add(new Object[]{entity.getId(), ATTR_ESTIMATE, null, null,
                Timestamp.from(entity.getEstimate().toInstant()), null, null});
        params.add(new Object[]{entity.getId(), ATTR_ACTUAL, null, null,
                Timestamp.from(entity.getActual().toInstant()), null, null});
        params.add(new Object[]{entity.getId(), ATTR_OVERTIME, null, null, null, entity.getOvertime().toNanos(), null});
        executeBatchUpdate(sql, params);
    }

    @Override
    protected void addRefs(Task entity) {
        String sql = loadSqlStatement(SQL_REF_ADD_PATH);
        List<Object[]> refs = new ArrayList<>(2);
        refs.add(new Object[]{entity.getId(), ATTR_QUALIFICATION, entity.getQualification().getId()});
        refs.add(new Object[]{entity.getId(), ATTR_SPRINT, entity.getSprint().getId()});
        executeBatchUpdate(sql, refs);
    }

    @Override
    protected void updateObject(Task entity) {
        String sql = loadSqlStatement(SQL_OBJECT_UPDATE_PATH);
        executeUpdate(sql, entity.getVersion() + 1, entity.getName(), entity.getDesc(), entity.getId());
    }

    @Override
    protected void updateParams(Task entity) {
        String sql = loadSqlStatement(SQL_PARAM_UPDATE_PATH);
        List<Object[]> params = new ArrayList<>(4);
        params.add(new Object[]{null, null, null, null, entity.getActive(), entity.getId(), ATTR_ACTIVE});
        params.add(new Object[]{null, null, Timestamp.from(entity.getEstimate().toInstant()), null, null,
                entity.getId(), ATTR_ESTIMATE});
        params.add(new Object[]{null, null, Timestamp.from(entity.getActual().toInstant()), null, null,
                entity.getId(), ATTR_ACTUAL});
        params.add(new Object[]{null, null, null, entity.getOvertime().toNanos(), null, entity.getId(), ATTR_OVERTIME});
        executeBatchUpdate(sql, params);
    }

    @Override
    protected void updateRefs(Task entity) {
        deleteRefs(entity.getId());
        addRefs(entity);
    }

    @Override
    protected void updateVersion(Task entity) {
        entity.setVersion(entity.getVersion() + 1);
    }

    @Override
    protected Task mapObject(@Nullable ObjectRow object) {
        if (object == null) return null;
        Task task = new Task();
        task.setId(object.getId());
        task.setVersion(object.getVersion());
        task.setName(object.getName());
        task.setDesc(object.getDesc());
        return task;
    }

    @Override
    protected void mapParams(@Nullable List<ParamRow> params, Task entity) {
        if (params.size() == 0) return;
        params.stream().forEach(param -> {
            switch (param.getAttrId().intValue()) {
                case ATTR_ACTIVE:
                    entity.setActive(param.getBooleanVal());
                    break;
                case ATTR_ESTIMATE:
                    entity.setEstimate(param.getDateVal());
                    break;
                case ATTR_ACTUAL:
                    entity.setActual(param.getDateVal());
                    break;
                case ATTR_OVERTIME:
                    entity.setOvertime(param.getIntervalVal());
                    break;
            }
        });
    }

    @Override
    protected void mapRefs(@Nullable List<RefRow> refs, Task entity) {
        if (refs.size() == 0) return;
        refs.stream().forEach(ref -> {
            switch (ref.getAttrId().intValue()) {
                case ATTR_QUALIFICATION:
                    Qualification qualification = new Qualification();
                    qualification.setId(ref.getRefId());
                    entity.setQualification(qualification);
                    break;
                case ATTR_SPRINT:
                    Sprint sprint = new Sprint();
                    sprint.setId(ref.getRefId());
                    entity.setSprint(sprint);
                    break;
            }
        });
    }
}
