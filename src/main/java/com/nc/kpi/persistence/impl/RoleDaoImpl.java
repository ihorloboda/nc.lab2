package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Role;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.RoleDao;
import com.nc.kpi.persistence.metamodel.rows.MetamodelObject;
import com.nc.kpi.persistence.metamodel.rows.Param;
import com.nc.kpi.persistence.metamodel.rows.Ref;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
    public Role find(@NotNull Long id) {
        String sqlRoles = loadSqlStatement(SQL_ROLE_FIND_PATH);
        String sqlObjects = loadSqlStatement(SQL_OBJECT_FIND_PATH);
        Role roleFromRoles = mapObject(findOne(sqlRoles, new roleRowMapper(), id));
        Role roleFromObjects = mapObject(findOne(sqlObjects, new ObjectRowMapper(), id));
        if ((roleFromRoles != null & roleFromObjects != null) && (roleFromRoles.equals(roleFromObjects))
                || (roleFromRoles == null && roleFromObjects == null)) {
            return roleFromRoles;
        } else {
            throw new DataIntegrityViolationException("Role must be stored in tables \"roles\" and \"objects\"");
        }
    }

    @Override
    @Transactional
    public void add(@NotNull Role entity) {
        String sqlRoles = loadSqlStatement(SQL_ROLE_ADD_PATH);
        String sqlObjects = loadSqlStatement(SQL_OBJECT_ADD_PATH);
        entity.setId(generateId(TYPE_ROLE));
        executeUpdate(sqlRoles, entity.getId(), entity.getName(), entity.getDesc());
        executeUpdate(sqlObjects, entity.getId(), null, TYPE_ROLE, entity.getName(), entity.getDesc());
    }

    @Override
    @Transactional
    public void update(@NotNull Role entity) {
        String sqlRoles = loadSqlStatement(SQL_ROLE_UPDATE_PATH);
        String sqlObjects = loadSqlStatement(SQL_OBJECT_UPDATE_PATH);
        executeUpdate(sqlRoles, entity.getName(), entity.getDesc(), entity.getId());
        executeUpdate(sqlObjects, null, entity.getName(), entity.getDesc(), entity.getId());
    }

    @Override
    @Transactional
    public void delete(@NotNull Long id) {
        String sqlRoles = loadSqlStatement(SQL_ROLE_DELETE_PATH);
        String sqlObjects = loadSqlStatement(SQL_OBJECT_DELETE_PATH);
        executeUpdate(sqlRoles, id);
        executeUpdate(sqlObjects, id);
    }

    private class roleRowMapper implements RowMapper<MetamodelObject> {
        @Override
        public MetamodelObject mapRow(ResultSet rs, int rowNum) throws SQLException {
            MetamodelObject object = new MetamodelObject();
            object.setId(rs.getLong(ROLE_ID));
            object.setName(rs.getString(ROLE_NAME));
            object.setDesc(rs.getString(ROLE_DESC));
            return object;
        }
    }

    @Override
    protected Role mapObject(MetamodelObject object) {
        if (object == null) {
            return null;
        }
        Role role = new Role();
        role.setId(object.getId());
        role.setName(object.getName());
        role.setDesc(object.getDesc());
        return role;
    }

    @Override
    protected void mapParams(List<Param> params, Role entity) {
        throw new UnsupportedOperationException("RoleDao mapParams");
    }

    @Override
    protected void mapRefs(List<Ref> refs, Role entity) {
        throw new UnsupportedOperationException("RoleDao mapRefs");
    }
}
