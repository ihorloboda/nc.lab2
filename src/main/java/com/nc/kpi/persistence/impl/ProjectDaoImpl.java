package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Project;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.ProjectDao;
import com.nc.kpi.persistence.metamodel.rows.MetamodelObject;
import com.nc.kpi.persistence.metamodel.rows.Param;
import com.nc.kpi.persistence.metamodel.rows.Ref;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ProjectDaoImpl extends AbstractDao<Project> implements ProjectDao {
    @Autowired
    public ProjectDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Project find(Long id) {
        return null;
    }

    @Override
    public void add(Project entity) {

    }

    @Override
    public void update(Project entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    protected Project mapObject(MetamodelObject object) {
        return null;
    }

    @Override
    protected Project mapParams(Param param, Project entity) {
        return null;
    }

    @Override
    protected Project mapRefs(Ref ref, Project entity) {
        return null;
    }
}
