package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Sprint;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.SprintDao;
import com.nc.kpi.persistence.metamodel.rows.ObjectRow;
import com.nc.kpi.persistence.metamodel.rows.ParamRow;
import com.nc.kpi.persistence.metamodel.rows.RefRow;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SprintDaoImpl extends AbstractDao<Sprint> implements SprintDao {
    @Autowired
    public SprintDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected void addObject(Sprint entity) {

    }

    @Override
    protected void addParams(Sprint entity) {

    }

    @Override
    protected void addRefs(Sprint entity) {

    }

    @Override
    protected void updateObject(Sprint entity) {

    }

    @Override
    protected void updateParams(Sprint entity) {

    }

    @Override
    protected void updateRefs(Sprint entity) {

    }

    @Override
    protected void updateVersion(Sprint entity) {

    }

    @Override
    protected Sprint mapObject(@Nullable ObjectRow object) {
        return null;
    }

    @Override
    protected void mapParams(@Nullable List<ParamRow> params, Sprint entity) {

    }

    @Override
    protected void mapRefs(@Nullable List<RefRow> refs, Sprint entity) {

    }
}
