package com.nc.kpi;

import com.nc.kpi.config.AppConfig;
import com.nc.kpi.entities.Qualification;
import com.nc.kpi.entities.Role;
import com.nc.kpi.entities.User;
import com.nc.kpi.persistence.CrudDao;
import com.nc.kpi.persistence.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.MailParseException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.scan("com.nc.kpi.persistence");
        ctx.refresh();
        CrudDao userDao = ctx.getBean(UserDao.class);
        userDao.update(null);
    }
}
