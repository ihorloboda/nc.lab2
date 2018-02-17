package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Qualification;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.QualificationDao;
import com.nc.kpi.persistence.metamodel.rows.ObjectRow;
import com.nc.kpi.persistence.metamodel.rows.ParamRow;
import com.nc.kpi.persistence.metamodel.rows.RefRow;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QualificationDaoImpl extends AbstractDao<Qualification> implements QualificationDao {
    @Autowired
    public QualificationDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Qualification find(@NotNull Long id) {
        return findObject(id);
    }

    @Override
    public void add(@NotNull Qualification entity) {
        addObject(entity);
    }

    @Override
    protected void addObject(Qualification entity) {
        String sql = loadSqlStatement(SQL_OBJECT_ADD_PATH);
        entity.setId(generateId(TYPE_QUALIFICATION));
        executeUpdate(sql, entity.getId(), null, TYPE_QUALIFICATION, entity.getName(), entity.getDesc(), TYPE_ROOT);
    }

    @Override
    protected void addParams(Qualification entity) {
        throw new UnsupportedOperationException("Qualification addParams");
    }

    @Override
    protected void addRefs(Qualification entity) {
        throw new UnsupportedOperationException("Qualification addRefs");
    }

    @Override
    public void update(@NotNull Qualification entity) {
        updateObject(entity);
    }

    @Override
    protected void updateObject(Qualification entity) {
        String sql = loadSqlStatement(SQL_OBJECT_UPDATE_PATH);
        executeUpdate(sql, null, entity.getName(), entity.getDesc(), entity.getId());
    }

    @Override
    protected void updateParams(Qualification entity) {
        throw new UnsupportedOperationException("Qualification updateParams");
    }

    @Override
    protected void updateRefs(Qualification entity) {
        throw new UnsupportedOperationException("Qualification updateRefs");
    }

    @Override
    protected void updateVersion(Qualification entity) {
        throw new UnsupportedOperationException("Qualification updateVersion");
    }

    @Override
    public void delete(@NotNull Long id) {
        deleteObject(id);
    }

    @Override
    protected Qualification mapObject(ObjectRow object) {
        if (object == null) return null;
        Qualification qualification = new Qualification();
        qualification.setId(object.getId());
        qualification.setName(object.getName());
        qualification.setDesc(object.getDesc());
        return qualification;
    }

    @Override
    protected void mapParams(List<ParamRow> params, Qualification entity) {
        throw new UnsupportedOperationException("QualificationDao mapParams");
    }

    @Override
    protected void mapRefs(List<RefRow> refs, Qualification entity) {
        throw new UnsupportedOperationException("QualificationDao mapRefs");
    }
}
