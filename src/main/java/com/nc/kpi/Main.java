package com.nc.kpi;

import com.nc.kpi.config.AppConfig;
import com.nc.kpi.persistence.UserDao;
import com.nc.kpi.persistence.metamodel.rows.ParamRow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.scan("com.nc.kpi.persistence");
        ctx.refresh();
        JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);
        String sql = "SELECT\n" +
                "  \"object_id\",\n" +
                "  \"attr_id\",\n" +
                "  \"number_val\",\n" +
                "  \"text_val\",\n" +
                "  \"date_val\",\n" +
                "  (EXTRACT(DAY FROM \"interval_val\") * 86400\n" +
                "   + EXTRACT(HOUR FROM \"interval_val\") * 3600\n" +
                "   + EXTRACT(MINUTE FROM \"interval_val\") * 60\n" +
                "   + EXTRACT(SECOND FROM \"interval_val\")) * 1E9 AS \"interval_val\",\n" +
                "  \"boolean_val\"\n" +
                "FROM \"params\"\n" +
                "WHERE \"object_id\" = ?";
        Duration duration = Duration.ofHours(29);
        List<ParamRow> paramRowList = jdbcTemplate.query(sql, new ParamRowMapper(), 5201802181942689L);
        paramRowList.stream().forEach(row ->{
            System.out.println(row.toString());
        });
    }

    protected static class ParamRowMapper implements RowMapper<ParamRow> {
        @Override
        public ParamRow mapRow(ResultSet rs, int rowNum) throws SQLException {
            ParamRow param = new ParamRow();
            param.setObjectId(rs.getLong("object_id"));
            param.setAttrId(rs.getLong("attr_id"));
            param.setBooleanVal(rs.getBoolean("boolean_val"));
            param.setNumberVal(rs.getLong("number_val"));
            param.setTextVal(rs.getString("text_val"));
            param.setDateVal(instantiateDate(rs.getTimestamp("date_val")));
            param.setIntervalVal(instantiateInterval(rs.getLong("interval_val")));
            log.info(param.toString());
            return param;
        }

        private Duration instantiateInterval(long nanos) {
            if (nanos != 0) return Duration.ofNanos(nanos);
            return null;
        }

        private OffsetDateTime instantiateDate(Timestamp timestamp) {
            if (timestamp != null) return OffsetDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault());
            return null;
        }
    }
}
