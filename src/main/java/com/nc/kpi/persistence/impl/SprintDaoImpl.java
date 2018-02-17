package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Project;
import com.nc.kpi.entities.Sprint;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.SprintDao;
import com.nc.kpi.persistence.metamodel.rows.ObjectRow;
import com.nc.kpi.persistence.metamodel.rows.ParamRow;
import com.nc.kpi.persistence.metamodel.rows.RefRow;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SprintDaoImpl extends AbstractDao<Sprint> implements SprintDao {
    @Autowired
    public SprintDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected void addObject(Sprint entity) {
        String sql = loadSqlStatement(SQL_OBJECT_ADD_PATH);
        entity.setId(generateId(TYPE_SPRINT));
        entity.setVersion(DEFAULT_OBJECT_VERSION);
        executeUpdate(sql, entity.getId(), entity.getVersion(), TYPE_SPRINT, entity.getName(), entity.getDesc());
    }

    @Override
    protected void addParams(Sprint entity) {
        String sql = loadSqlStatement(SQL_PARAM_ADD_PATH);
        executeUpdate(sql, entity.getId(), ATTR_ACTIVE, null, null, null, entity.getActive());
    }

    @Override
    protected void addRefs(Sprint entity) {
        String sql = loadSqlStatement(SQL_REF_ADD_PATH);
        List<Object[]> refs = new ArrayList<>(2);
        if (entity.getPrevSprint() != null)
            refs.add(new Object[]{entity.getId(), ATTR_PREV_SPRINT, entity.getPrevSprint().getId()});
        refs.add(new Object[]{entity.getId(), ATTR_PROJECT, entity.getProject().getId()});
        executeBatchUpdate(sql, refs);
    }

    @Override
    protected void updateObject(Sprint entity) {
        String sql = loadSqlStatement(SQL_OBJECT_UPDATE_PATH);
        executeUpdate(sql, entity.getVersion() + 1, entity.getName(), entity.getDesc(), entity.getId());
    }

    @Override
    protected void updateParams(Sprint entity) {
        String sql = loadSqlStatement(SQL_PARAM_UPDATE_PATH);
        executeUpdate(sql, null, null, null, entity.getActive(), entity.getId(), ATTR_ACTIVE);
    }

    @Override
    protected void updateRefs(Sprint entity) {
        deleteRefs(entity.getId());
        addRefs(entity);
    }

    @Override
    protected void updateVersion(Sprint entity) {
        entity.setVersion(entity.getVersion() + 1);
    }

    @Override
    protected Sprint mapObject(@Nullable ObjectRow object) {
        if (object == null) return null;
        Sprint sprint = new Sprint();
        sprint.setId(object.getId());
        sprint.setVersion(object.getVersion());
        sprint.setName(object.getName());
        sprint.setDesc(object.getDesc());
        return sprint;
    }

    @Override
    protected void mapParams(@Nullable List<ParamRow> params, Sprint entity) {
        if (params.size() == 0) return;
        if (params.size() > 1) throw new IncorrectResultSizeDataAccessException(1);
        entity.setActive(params.get(0).getBooleanVal());
    }

    @Override
    protected void mapRefs(@Nullable List<RefRow> refs, Sprint entity) {
        if (refs.size() == 0) return;
        refs.stream().forEach(ref -> {
            switch (ref.getAttrId().intValue()) {
                case ATTR_PROJECT:
                    Project project = new Project();
                    project.setId(ref.getRefId());
                    entity.setProject(project);
                    break;
                case ATTR_PREV_SPRINT:
                    Sprint sprint = new Sprint();
                    sprint.setId(ref.getRefId());
                    entity.setPrevSprint(sprint);
                    break;
            }
        });
    }
}
