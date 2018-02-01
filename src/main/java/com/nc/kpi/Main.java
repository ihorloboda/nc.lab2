package com.nc.kpi;

import com.nc.kpi.config.AppConfig;
import com.nc.kpi.config.DBConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(DBConfig.class);
        ctx.register(AppConfig.class);
        ctx.scan("com.nc.kpi.persistence");
        ctx.refresh();
        JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);
        jdbcTemplate.update("INSERT INTO \"objects\" (\"object_id\", \"type_id\", \"object_name\") VALUES ('29', '2', 'name'); " +
                "INSERT INTO \"objects\" (\"object_id\", \"type_id\", \"object_name\") VALUES ('666', '2', 'name')");
    }
}
