package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Role;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.RoleDao;
import com.nc.kpi.persistence.metamodel.rows.ObjectRow;
import com.nc.kpi.persistence.metamodel.rows.ParamRow;
import com.nc.kpi.persistence.metamodel.rows.RefRow;
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
        Role roleFromRoles = findRole(id);
        Role roleFromObjects = findObject(id);
        if ((roleFromRoles != null & roleFromObjects != null) && (roleFromRoles.equals(roleFromObjects))
                || (roleFromRoles == null && roleFromObjects == null)) {
            return roleFromRoles;
        } else {
            throw new DataIntegrityViolationException("Role must be stored in tables \"roles\" and \"objects\"");
        }
    }

    private Role findRole(Long id) {
        String sql = loadSqlStatement(SQL_ROLE_FIND_PATH);
        return mapObject(findOne(sql, new roleRowMapper(), id));
    }

    @Override
    @Transactional
    public void add(@NotNull Role entity) {
        addObject(entity);
        addRole(entity);
    }

    private void addRole(Role entity) {
        String sql = loadSqlStatement(SQL_ROLE_ADD_PATH);
        executeUpdate(sql, entity.getId(), entity.getName(), entity.getDesc());
    }

    @Override
    protected void addObject(Role entity) {
        String sql = loadSqlStatement(SQL_OBJECT_ADD_PATH);
        entity.setId(generateId(TYPE_ROLE));
        executeUpdate(sql, entity.getId(), null, TYPE_ROLE, entity.getName(), entity.getDesc());
    }

    @Override
    protected void addParams(Role entity) {
        throw new UnsupportedOperationException("Role addParams");
    }

    @Override
    protected void addRefs(Role entity) {
        throw new UnsupportedOperationException("Role addRefs");
    }

    @Override
    @Transactional
    public void update(@NotNull Role entity) {
        updateObject(entity);
        updateRole(entity);
    }

    private void updateRole(Role entity) {
        String sql = loadSqlStatement(SQL_ROLE_UPDATE_PATH);
        executeUpdate(sql, entity.getName(), entity.getDesc(), entity.getId());
    }

    @Override
    protected void updateObject(Role entity) {
        String sql = loadSqlStatement(SQL_OBJECT_UPDATE_PATH);
        executeUpdate(sql, null, entity.getName(), entity.getDesc(), entity.getId());
    }

    @Override
    protected void updateParams(Role entity) {
        throw new UnsupportedOperationException("Role updateParams");
    }

    @Override
    protected void updateRefs(Role entity) {
        throw new UnsupportedOperationException("Role updateRefs");
    }

    @Override
    protected void updateVersion(Role entity) {
        throw new UnsupportedOperationException("Role updateVersion");
    }

    @Override
    @Transactional
    public void delete(@NotNull Long id) {
        deleteObject(id);
        deleteRole(id);
    }

    private void deleteRole(Long id) {
        String sql = loadSqlStatement(SQL_ROLE_DELETE_PATH);
        executeUpdate(sql, id);
    }

    private class roleRowMapper implements RowMapper<ObjectRow> {
        @Override
        public ObjectRow mapRow(ResultSet rs, int rowNum) throws SQLException {
            ObjectRow object = new ObjectRow();
            object.setId(rs.getLong(ROLE_ID));
            object.setName(rs.getString(ROLE_NAME));
            object.setDesc(rs.getString(ROLE_DESC));
            return object;
        }
    }

    @Override
    protected Role mapObject(ObjectRow object) {
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
    protected void mapParams(List<ParamRow> params, Role entity) {
        throw new UnsupportedOperationException("RoleDao mapParams");
    }

    @Override
    protected void mapRefs(List<RefRow> refs, Role entity) {
        throw new UnsupportedOperationException("RoleDao mapRefs");
    }
}
