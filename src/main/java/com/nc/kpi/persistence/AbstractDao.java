package com.nc.kpi.persistence;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractDao<T> {
    private final JdbcTemplate jdbcTemplate;
    private final String STATEMENTS_PATH = "src/main/resources/db/statements/";
    private final Integer BOUND = 10;

    protected String loadSqlStatement(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Files.lines(Paths.get(STATEMENTS_PATH + path), StandardCharsets.UTF_8).forEach(stringBuilder::append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    protected int executeUpdate(String sql, Object... params) {
        return jdbcTemplate.update(sql, params);
    }

    protected T findOne(String sql, RowMapper<T> rowMapper, Object... params) {
        List<T> query = jdbcTemplate.query(sql, rowMapper, params);
        switch (query.size()) {
            case 0:
                return null;
            case 1:
                return query.get(0);
            default:
                throw new IncorrectResultSizeDataAccessException(1);
        }
    }

    protected List<T> findMultiple(String sql, RowMapper<T> rowMapper, Object... params) {
        return jdbcTemplate.query(sql, rowMapper, params);
    }

    protected Long generateId(Integer typeId) {
        Random random = new Random(System.currentTimeMillis());
        String id = typeId.toString().concat(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString()
                        .replaceAll("-", "")
                        .replaceAll("T", "")
                        .replaceAll(":", "")
        ).concat(String.valueOf(random.nextInt(BOUND)));
        return Long.valueOf(id);
    }
}
