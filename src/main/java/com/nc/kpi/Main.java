package com.nc.kpi;

import com.nc.kpi.config.AppConfig;
import com.nc.kpi.persistence.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.time.Duration;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.scan("com.nc.kpi.persistence");
        ctx.refresh();
        JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);
        String sql = "INSERT INTO a VALUES (NUMTODSINTERVAL(?/1E9, 'SECOND'))";
        Duration duration = Duration.ofHours(29);
        jdbcTemplate.update(sql, duration.toNanos());
    }
}
