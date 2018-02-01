package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Role;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    @Autowired
    public RoleDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Role find(Long id) {
        return null;
    }

    @Override
    public void add(Role entity) {
    }

    @Override
    public void update(Role entity) {

    }

    @Override
    public void delete(Long id) {

    }
}
