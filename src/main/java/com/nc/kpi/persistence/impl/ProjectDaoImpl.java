package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Project;
import com.nc.kpi.entities.User;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.ProjectDao;
import com.nc.kpi.persistence.metamodel.rows.ObjectRow;
import com.nc.kpi.persistence.metamodel.rows.ParamRow;
import com.nc.kpi.persistence.metamodel.rows.RefRow;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectDaoImpl extends AbstractDao<Project> implements ProjectDao {
    @Autowired
    public ProjectDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional
    public void add(Project entity) {
        addObject(entity);
        addParams(entity);
        addRefs(entity);
    }

    @Override
    protected void addObject(Project entity) {
        String sqlObjectAdd = loadSqlStatement(SQL_OBJECT_ADD_PATH);
        entity.setId(generateId(TYPE_PROJECT));
        entity.setVersion(DEFAULT_OBJECT_VERSION);
        executeUpdate(sqlObjectAdd, entity.getId(), entity.getVersion(), TYPE_PROJECT, entity.getName(), entity.getDesc());
    }

    @Override
    //TODO boolean
    protected void addParams(Project entity) {
        String sqlParamAdd = loadSqlStatement(SQL_PARAM_ADD_PATH);
        List<Object[]> params = new ArrayList<>(3);
        params.add(new Object[]{entity.getId(), ATTR_ACTIVE, null, null, null, entity.getActive()});
        params.add(new Object[]{entity.getId(), ATTR_START_DATE, null, null, Timestamp.from(entity.getStartDate().toInstant()), null});
        params.add(new Object[]{entity.getId(), ATTR_END_DATE, null, null, Timestamp.from(entity.getEndDate().toInstant()), null});
        executeBatchUpdate(sqlParamAdd, params);
    }

    @Override
    protected void addRefs(Project entity) {
        String sql = loadSqlStatement(SQL_REF_ADD_PATH);
        List<Object[]> refs = new ArrayList<>(2);
        if (entity.getCustomer() != null)
            refs.add(new Object[]{entity.getId(), ATTR_CUSTOMER, entity.getCustomer().getId()});
        if (entity.getManager() != null)
            refs.add(new Object[]{entity.getId(), ATTR_MANAGER, entity.getManager().getId()});
        if (refs.size() != 0)
            executeBatchUpdate(sql, refs);
    }

    @Override
    protected void updateObject(Project entity) {
        String sql = loadSqlStatement(SQL_OBJECT_UPDATE_PATH);
        executeUpdate(sql, entity.getVersion() + 1, entity.getName(), entity.getDesc(), entity.getId());
    }

    @Override
    protected void updateParams(Project entity) {
        String sql = loadSqlStatement(SQL_PARAM_UPDATE_PATH);
        List<Object[]> batchArgs = new ArrayList<>();
        batchArgs.add(new Object[]{null, null, null, entity.getActive(), entity.getId(), ATTR_ACTIVE,});
        batchArgs.add(new Object[]{null, null, Timestamp.from(entity.getStartDate().toInstant()), null, entity.getId(), ATTR_START_DATE});
        batchArgs.add(new Object[]{null, null, Timestamp.from(entity.getEndDate().toInstant()), null, entity.getId(), ATTR_END_DATE});
        executeBatchUpdate(sql, batchArgs);
    }

    @Override
    protected void updateRefs(Project entity) {
        deleteRefs(entity.getId());
        addRefs(entity);
    }

    @Override
    protected void updateVersion(Project entity) {
        entity.setVersion(entity.getVersion() + 1);
    }

    @Override
    protected Project mapObject(@Nullable ObjectRow object) {
        if (object == null) return null;
        Project project = new Project();
        project.setId(object.getId());
        project.setVersion(object.getVersion());
        project.setName(object.getName());
        project.setDesc(object.getDesc());
        return project;
    }

    @Override
    protected void mapParams(@Nullable List<ParamRow> params, Project entity) {
        if (params.size() == 0) return;
        params.stream().forEach(param -> {
            switch (param.getAttrId().intValue()) {
                case ATTR_ACTIVE:
                    entity.setActive(param.getBooleanVal());
                    break;
                case ATTR_START_DATE:
                    entity.setStartDate(param.getDateVal());
                    break;
                case ATTR_END_DATE:
                    entity.setEndDate(param.getDateVal());
                    break;
            }
        });
    }

    @Override
    protected void mapRefs(@Nullable List<RefRow> refs, Project entity) {
        if (refs.size() == 0) return;
        refs.stream().forEach(ref -> {
            switch (ref.getAttrId().intValue()) {
                case ATTR_MANAGER:
                    User manager = new User();
                    manager.setId(ref.getRefId());
                    entity.setManager(manager);
                    break;
                case ATTR_CUSTOMER:
                    User customer = new User();
                    customer.setId(ref.getRefId());
                    entity.setCustomer(customer);
                    break;
            }
        });
    }
}
