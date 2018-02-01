package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.User;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private final String SQL_FIND_PATH = "users/find.sql";
    private final String SQL_ADD_PATH = "users/add.sql";

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public User find(Long id) {
        return null;
    }

    @Override
    public void add(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(Long id) {

    }
}
