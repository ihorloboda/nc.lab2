package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Grant;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.GrantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class GrantDaoImpl extends AbstractDao<Grant<?>> implements GrantDao {
    @Autowired
    public GrantDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Grant<?> find(Long id) {
        return null;
    }

    @Override
    public void add(Grant<?> entity) {

    }

    @Override
    public void update(Grant<?> entity) {

    }

    @Override
    public void delete(Long id) {

    }
}
