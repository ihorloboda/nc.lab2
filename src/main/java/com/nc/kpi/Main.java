package com.nc.kpi;

import com.nc.kpi.config.AppConfig;
import com.nc.kpi.entities.Qualification;
import com.nc.kpi.entities.Role;
import com.nc.kpi.entities.User;
import com.nc.kpi.persistence.UserDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.scan("com.nc.kpi.persistence");
        ctx.refresh();
        UserDao userDao = ctx.getBean(UserDao.class);
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
        role.setDesc("Role which has access to any type and object");
        User user = new User();
        List<Role> roles = new ArrayList<>();
        Qualification qualification = new Qualification();
        qualification.setId(2L);
        qualification.setName("JUNIOR");
        qualification.setDesc("The lowest qualification");
        roles.add(role);
        user.setName("kaban");
        user.setDesc("desc");
        user.setBio("bio");
        user.setQualification(qualification);
        user.setRoles(roles);
        userDao.add(user);
    }
}
