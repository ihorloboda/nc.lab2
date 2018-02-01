package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Project;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.ProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
