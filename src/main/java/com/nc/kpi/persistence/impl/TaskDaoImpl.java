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
        executeUpdate(sql, entity.getId(), DEFAULT_OBJECT_VERSION, TYPE_TASK, entity.getName(), entity.getDesc(), TYPE_SPRINT);
    }

    @Override
    protected void addParams(Task entity) {
        String sql = loadSqlStatement(SQL_PARAM_ADD_PATH);
        List<Object[]> params = new ArrayList<>();

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
