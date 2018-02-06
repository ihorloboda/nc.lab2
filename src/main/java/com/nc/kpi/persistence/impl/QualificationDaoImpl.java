package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Qualification;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.QualificationDao;
import com.nc.kpi.persistence.metamodel.rows.MetamodelObject;
import com.nc.kpi.persistence.metamodel.rows.Param;
import com.nc.kpi.persistence.metamodel.rows.Ref;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QualificationDaoImpl extends AbstractDao<Qualification> implements QualificationDao {
    @Autowired
    public QualificationDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Qualification find(Long id) {
        String sql = loadSqlStatement(SQL_OBJECT_FIND_PATH);
        return mapObject(findOne(sql, new ObjectRowMapper(), id));
    }

    @Override
    public void add(Qualification entity) {
        String sql = loadSqlStatement(SQL_OBJECT_ADD_PATH);
        entity.setId(generateId(TYPE_QUALIFICATION));
        executeUpdate(sql, entity.getId(), null, TYPE_QUALIFICATION, entity.getName(), entity.getDesc());
    }

    @Override
    public void update(Qualification entity) {
        String sql = loadSqlStatement(SQL_OBJECT_UPDATE_PATH);
        executeUpdate(sql, null, entity.getName(), entity.getDesc(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = loadSqlStatement(SQL_OBJECT_DELETE_PATH);
        executeUpdate(sql, id);
    }

    @Override
    protected Qualification mapObject(MetamodelObject object) {
        if (object == null) {
            return null;
        }
        Qualification qualification = new Qualification();
        qualification.setId(object.getId());
        qualification.setName(object.getName());
        qualification.setDesc(object.getDesc());
        return qualification;
    }

    @Override
    protected Qualification mapParams(Param param, Qualification entity) {
        throw new UnsupportedOperationException("QualificationDao mapParams");
    }

    @Override
    protected Qualification mapRefs(Ref ref, Qualification entity) {
        throw new UnsupportedOperationException("QualificationDao mapRefs");
    }
}
