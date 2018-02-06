package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Qualification;
import com.nc.kpi.entities.User;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO add managed and ordered projects
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
        String sqlRefFind = loadSqlStatement(SQL_REF_FIND_PATH);


        return null;
    }

    @Override
    @Transactional
    //TODO read chapter 10 Spring transactions
    public void add(User entity) {
        String sqlObjectAdd = loadSqlStatement(SQL_OBJECT_ADD_PATH);
        String sqlRefAdd = loadSqlStatement(SQL_REF_ADD_PATH);
        entity.setId(generateId(TYPE_USER));
        executeUpdate(sqlObjectAdd, entity.getId(), TYPE_USER, entity.getName(), entity.getDesc());
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
    protected User mapObject(Map<String, ?> objectMap) {
        return null;
    }

    @Override
    protected User mapParams(Map<String, ?> paramMap, User entity) {
        return null;
    }

    @Override
    protected User mapRefs(Map<String, ?> refMap, User entity) {
        return null;
    }

    private class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong(OBJECT_ID));
            user.setName(rs.getString(OBJECT_NAME));
            user.setDesc(rs.getString(OBJECT_DESC));
            return user;
        }
    }

    private class UserRefsRowMapper implements RowMapper<Map<String, Object>> {
        @Override
        public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
            Map<String, Object> refs = new HashMap<>();
            Qualification qualification = new Qualification();
            return refs;
        }
    }
}
