package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Qualification;
import com.nc.kpi.entities.Role;
import com.nc.kpi.entities.User;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.UserDao;
import com.nc.kpi.persistence.metamodel.rows.MetamodelObject;
import com.nc.kpi.persistence.metamodel.rows.Param;
import com.nc.kpi.persistence.metamodel.rows.Ref;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public User find(@NotNull Long id) {
        String sqlObjectFind = loadSqlStatement(SQL_OBJECT_FIND_PATH);
        String sqlParamsFind = loadSqlStatement(SQL_PARAM_FIND_PATH);
        String sqlRefsFind = loadSqlStatement(SQL_REF_FIND_PATH);
        User user = mapObject(findOne(sqlObjectFind, new ObjectRowMapper(), id));
        mapRefs(findMultiple(sqlRefsFind, new RefRowMapper(), id), user);
        mapParams(findMultiple(sqlParamsFind, new ParamRowMapper(), id), user);
        return user;
    }

    @Override
    @Transactional
    public void add(@NotNull User entity) {
        String sqlObjectAdd = loadSqlStatement(SQL_OBJECT_ADD_PATH);
        String sqlParamAdd = loadSqlStatement(SQL_PARAM_ADD_PATH);
        entity.setId(generateId(TYPE_USER));
        entity.setVersion(DEFAULT_OBJECT_VERSION);
        executeUpdate(sqlObjectAdd, entity.getId(), entity.getVersion(), TYPE_USER, entity.getName(), entity.getDesc());
        executeUpdate(sqlParamAdd, entity.getId(), ATTR_BIO, null, entity.getBio(), null, null);
        addUserRefs(entity);
    }

    @Override
    @Transactional
    public void update(@NotNull User entity) {
        String sqlObjectUpdate = loadSqlStatement(SQL_OBJECT_UPDATE_PATH);
        String sqlParamUpdate = loadSqlStatement(SQL_PARAM_UPDATE_PATH);
        executeUpdate(sqlObjectUpdate, entity.getVersion() + 1, entity.getName(), entity.getDesc(), entity.getId());
        executeUpdate(sqlParamUpdate, null, entity.getBio(), null, null, entity.getId(), ATTR_BIO);
        updateUserRefs(entity);
        entity.setVersion(entity.getVersion() + 1);
    }

    @Override
    @Transactional
    public void delete(@NotNull Long id) {
        String sqlObjectDelete = loadSqlStatement(SQL_OBJECT_DELETE_PATH);
        String sqlParamDelete = loadSqlStatement(SQL_PARAM_DELETE_PATH);
        String sqlRefDelete = loadSqlStatement(SQL_REF_DELETE_ALL_PATH);
        executeUpdate(sqlParamDelete, id, ATTR_BIO);
        executeUpdate(sqlRefDelete, id);
        executeUpdate(sqlObjectDelete, id);
    }

    private void addUserRefs(User entity) {
        String sqlRefAdd = loadSqlStatement(SQL_REF_ADD_PATH);
        List<Object[]> batchArgs = new ArrayList<>();
        entity.getRoles().stream().forEach(role -> {
            batchArgs.add(new Object[]{entity.getId(), ATTR_ROLES, role.getId()});
        });
        batchArgs.add(new Object[]{entity.getId(), ATTR_QUALIFICATION, entity.getQualification().getId()});
        executeBatchUpdate(sqlRefAdd, batchArgs);
    }

    private void updateUserRefs(User entity) {
        deleteUserRefs(entity);
        addUserRefs(entity);
    }

    private void deleteUserRefs(User entity) {
        String sqlRefDelete = loadSqlStatement(SQL_REF_DELETE_ALL_PATH);
        executeUpdate(sqlRefDelete, entity.getId());
    }

    @Override
    protected User mapObject(@Nullable MetamodelObject object) {
        if (object == null) return null;
        User user = new User();
        user.setId(object.getId());
        user.setVersion(object.getVersion());
        user.setName(object.getName());
        user.setDesc(object.getDesc());
        return user;
    }

    @Override
    protected void mapParams(@Nullable List<Param> params, User entity) {
        if (params.size() == 0) return;
        if (params.size() != 1) {
            throw new IncorrectResultSizeDataAccessException(1);
        }
        entity.setBio(params.get(0).getTextVal());
    }

    @Override
    protected void mapRefs(@Nullable List<Ref> refs, User entity) {
        if (refs.size() == 0) return;
        entity.setRoles(new ArrayList<>());
        refs.stream().forEach((Ref ref) -> {
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
