package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Role;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    private final String SQL_ROLE_ADD_PATH = "roles/add.sql";
    private final String SQL_ROLE_FIND_PATH = "roles/find.sql";
    private final String SQL_ROLE_UPDATE_PATH = "roles/update.sql";
    private final String SQL_ROLE_DELETE_PATH = "roles/delete.sql";

    @Autowired
    public RoleDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional
    public Role find(Long id) {
        String sqlRoles = loadSqlStatement(SQL_ROLE_FIND_PATH);
        String sqlObjects = loadSqlStatement(SQL_OBJECT_FIND_PATH);
        Role roleFromRoles = mapRole(findOne(sqlRoles, new RoleMapRowMapper(), id), ROLE_ID, ROLE_NAME, ROLE_DESC);
        Role roleFromObjects = mapObject(findOne(sqlObjects, new ObjectMapRowMapper(), id));
        if ((roleFromRoles != null & roleFromObjects != null) && (roleFromRoles.equals(roleFromObjects))
                || (roleFromRoles == null && roleFromObjects == null)) {
            return roleFromRoles;
        } else {
            throw new DataIntegrityViolationException("Role must be stored in tables \"roles\" and \"objects\"");
        }
    }

    @Override
    @Transactional
    public void add(Role entity) {
        String sqlRoles = loadSqlStatement(SQL_ROLE_ADD_PATH);
        String sqlObjects = loadSqlStatement(SQL_OBJECT_ADD_PATH);
        entity.setId(generateId(TYPE_ROLE));
        executeUpdate(sqlRoles, entity.getId(), entity.getName(), entity.getDesc());
        executeUpdate(sqlObjects, entity.getId(), null, TYPE_ROLE, entity.getName(), entity.getDesc());
    }

    @Override
    @Transactional
    public void update(Role entity) {
        String sqlRoles = loadSqlStatement(SQL_ROLE_UPDATE_PATH);
        String sqlObjects = loadSqlStatement(SQL_OBJECT_UPDATE_PATH);
        executeUpdate(sqlRoles, entity.getName(), entity.getDesc(), entity.getId());
        executeUpdate(sqlObjects, null, entity.getName(), entity.getDesc(), entity.getId());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        String sqlRoles = loadSqlStatement(SQL_ROLE_DELETE_PATH);
        String sqlObjects = loadSqlStatement(SQL_OBJECT_DELETE_PATH);
        executeUpdate(sqlRoles, id);
        executeUpdate(sqlObjects, id);
    }

    private class RoleMapRowMapper implements RowMapper<Map<String, Object>> {
        @Override
        public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
            Map<String, Object> roleMap = new HashMap<>();
            roleMap.put(ROLE_ID, rs.getLong(ROLE_ID));
            roleMap.put(ROLE_NAME, rs.getString(ROLE_NAME));
            roleMap.put(ROLE_DESC, rs.getString(ROLE_DESC));
            return roleMap;
        }
    }

    private Role mapRole(Map<String, ?> roleMap, String idParam, String nameParam, String descParam) {
        if (roleMap == null) {
            return null;
        }
        Role role = new Role();
        role.setId((Long) roleMap.get(idParam));
        role.setName((String) roleMap.get(nameParam));
        role.setDesc((String) roleMap.get(descParam));
        return role;
    }

    @Override
    protected Role mapObject(Map<String, ?> objectMap) {
        return mapRole(objectMap, OBJECT_ID, OBJECT_NAME, OBJECT_DESC);
    }

    @Override
    protected Role mapParams(Map<String, ?> paramMap, Role entity) {
        throw new UnsupportedOperationException("RoleDao mapParams");
    }

    @Override
    protected Role mapRefs(Map<String, ?> refMap, Role entity) {
        throw new UnsupportedOperationException("RoleDao mapRefs");
    }
}
