package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Qualification;
import com.nc.kpi.entities.Role;
import com.nc.kpi.entities.User;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.UserDao;
import com.nc.kpi.persistence.metamodel.rows.ObjectRow;
import com.nc.kpi.persistence.metamodel.rows.ParamRow;
import com.nc.kpi.persistence.metamodel.rows.RefRow;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//TODO read chapter 10 Spring transactions
//TODO add managed and ordered projects, tasks
//TODO make this code more beautiful
//TODO generalize some methods
@Repository
@Slf4j
public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected void addObject(User entity) {
        String sqlObjectAdd = loadSqlStatement(SQL_OBJECT_ADD_PATH);
        entity.setId(generateId(TYPE_USER));
        entity.setVersion(DEFAULT_OBJECT_VERSION);
        executeUpdate(sqlObjectAdd, entity.getId(), entity.getVersion(), TYPE_USER, entity.getName(), entity.getDesc(), TYPE_ROOT);
    }

    @Override
    protected void addParams(User entity) {
        String sqlParamAdd = loadSqlStatement(SQL_PARAM_ADD_PATH);
        executeUpdate(sqlParamAdd, entity.getId(), ATTR_BIO, null, entity.getBio(), null, null);
    }

    @Override
    protected void addRefs(User entity) {
        if (entity.getRoles() == null) return;
        String sqlRefAdd = loadSqlStatement(SQL_REF_ADD_PATH);
        List<Object[]> batchArgs = new ArrayList<>();
        entity.getRoles().stream().forEach(role -> {
            batchArgs.add(new Object[]{entity.getId(), ATTR_ROLES, role.getId()});
        });
        batchArgs.add(new Object[]{entity.getId(), ATTR_QUALIFICATION, entity.getQualification().getId()});
        executeBatchUpdate(sqlRefAdd, batchArgs);
    }

    @Override
    protected void updateObject(User entity) {
        String sqlObjectUpdate = loadSqlStatement(SQL_OBJECT_UPDATE_PATH);
        executeUpdate(sqlObjectUpdate, entity.getVersion() + 1, entity.getName(), entity.getDesc(), entity.getId());
    }

    @Override
    protected void updateParams(User entity) {
        String sqlParamUpdate = loadSqlStatement(SQL_PARAM_UPDATE_PATH);
        executeUpdate(sqlParamUpdate, null, entity.getBio(), null, null, entity.getId(), ATTR_BIO);
    }

    @Override
    protected void updateRefs(User entity) {
        deleteRefs(entity.getId());
        addRefs(entity);
    }

    @Override
    protected void updateVersion(User entity) {
        entity.setVersion(entity.getVersion() + 1);
    }

    @Override
    protected User mapObject(@Nullable ObjectRow object) {
        if (object == null) return null;
        User user = new User();
        user.setId(object.getId());
        user.setVersion(object.getVersion());
        user.setName(object.getName());
        user.setDesc(object.getDesc());
        return user;
    }

    @Override
    protected void mapParams(@Nullable List<ParamRow> params, User entity) {
        if (params.size() == 0) return;
        if (params.size() != 1) {
            throw new IncorrectResultSizeDataAccessException(1);
        }
        entity.setBio(params.get(0).getTextVal());
    }

    @Override
    protected void mapRefs(@Nullable List<RefRow> refs, User entity) {
        if (refs.size() == 0) return;
        entity.setRoles(new ArrayList<>());
        refs.stream().forEach((RefRow ref) -> {
            switch (ref.getAttrId().intValue()) {
                case ATTR_QUALIFICATION:
                    Qualification qualification = new Qualification();
                    qualification.setId(ref.getRefId());
                    entity.setQualification(qualification);
                    break;
                case ATTR_ROLES:
                    Role role = new Role();
                    role.setId(ref.getRefId());
                    entity.getRoles().add(role);
                    break;
            }
        });
        if (entity.getRoles().size() == 0) {
            entity.setRoles(null);
        }
    }
}
