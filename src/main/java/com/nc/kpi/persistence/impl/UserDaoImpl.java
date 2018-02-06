package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.User;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.UserDao;
import com.nc.kpi.persistence.metamodel.rows.MetamodelObject;
import com.nc.kpi.persistence.metamodel.rows.Param;
import com.nc.kpi.persistence.metamodel.rows.Ref;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//TODO add managed and ordered projects, tasks
@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional
    public User find(Long id) {
        String sqlObjectFind = loadSqlStatement(SQL_OBJECT_FIND_PATH);
        String sqlParamsFind = loadSqlStatement(SQL_PARAM_FIND_PATH);
        String sqlRefsFind = loadSqlStatement(SQL_REF_FIND_PATH);
        User user = mapObject(findOne(sqlObjectFind, new ObjectRowMapper(), id));
        //TODO

        return null;
    }

    @Override
    @Transactional
    //TODO read chapter 10 Spring transactions
    public void add(User entity) {
        String sqlObjectAdd = loadSqlStatement(SQL_OBJECT_ADD_PATH);
        String sqlTextParamAdd = loadSqlStatement(SQL_PARAM_ADD_PATH);
        String sqlRefAdd = loadSqlStatement(SQL_REF_ADD_PATH);
        entity.setId(generateId(TYPE_USER));
        entity.setVersion(DEFAULT_OBJECT_VERSION);
        executeUpdate(sqlObjectAdd, entity.getId(), entity.getVersion(), TYPE_USER, entity.getName(), entity.getDesc());
        executeUpdate(sqlTextParamAdd, entity.getId(), ATTR_BIO, entity.getBio());
        executeUpdate(sqlRefAdd, entity.getId(), ATTR_QUALIFICATION, entity.getQualification().getId());
        executeBatchUpdate(sqlRefAdd, getBatchRefs(entity));
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(Long id) {

    }

    private List<Object[]> getBatchRefs(User entity) {
        List<Object[]> refs = new ArrayList<>();
        entity.getRoles().stream().forEach(role -> {
            Object[] roleRow = {entity.getId(), ATTR_ROLES, role.getId()};
            refs.add(roleRow);
        });
        return refs;
    }

    @Override
    protected User mapObject(MetamodelObject object) {
        return null;
    }

    @Override
    protected User mapParams(Param param, User entity) {
        return null;
    }

    @Override
    protected User mapRefs(Ref ref, User entity) {
        return null;
    }
}
