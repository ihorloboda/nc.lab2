package com.nc.kpi.persistence;

import com.nc.kpi.persistence.metamodel.rows.MetamodelObject;
import com.nc.kpi.persistence.metamodel.rows.Param;
import com.nc.kpi.persistence.metamodel.rows.Ref;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static lombok.AccessLevel.PUBLIC;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractDao<T> {
    //bounds for random number which added to new object id
    private final Integer MAX_EXCLUSIVE_BOUND = 1000;
    private final Integer MIN_INCLUSIVE_BOUND = 100;

    private final JdbcTemplate jdbcTemplate;

    //metamodel default version of object
    protected final Long DEFAULT_OBJECT_VERSION = 1L;

    //metamodel type constants
    protected final Integer TYPE_ROLE = 1;
    protected final Integer TYPE_QUALIFICATION = 2;
    protected final Integer TYPE_USER = 3;
    protected final Integer TYPE_PROJECT = 4;
    protected final Integer TYPE_SPRINT = 5;
    protected final Integer TYPE_TASK = 6;

    //metamodel attribute constants
    protected final Integer ATTR_BIO = 1;
    protected final Integer ATTR_ROLES = 2;
    protected final Integer ATTR_QUALIFICATION = 3;
    protected final Integer ATTR_START_DATE = 4;
    protected final Integer ATTR_END_DATE = 5;
    protected final Integer ATTR_ACTIVE = 6;
    protected final Integer ATTR_MANAGER = 7;
    protected final Integer ATTR_CUSTOMER = 8;
    protected final Integer ATTR_PROJECT = 9;
    protected final Integer ATTR_PREV_SPRINT = 10;
    protected final Integer ATTR_SPRINT = 11;
    protected final Integer ATTR_ESTIMATE = 12;
    protected final Integer ATTR_ACTUAL = 13;
    protected final Integer ATTR_OVERTIME = 14;
    protected final Integer ATTR_SUBTASKS = 15;
    protected final Integer ATTR_PREV_TASKS = 16;
    protected final Integer ATTR_EMPLOYEE = 17;

    //pathes to sql statements
    private final String STATEMENTS_PATH = "src/main/resources/db/statements/";

    protected final String SQL_OBJECT_ADD_PATH = "objects/add.sql";
    protected final String SQL_OBJECT_DELETE_PATH = "objects/delete.sql";
    protected final String SQL_OBJECT_FIND_PATH = "objects/find.sql";
    protected final String SQL_OBJECT_UPDATE_PATH = "objects/update.sql";

    protected final String SQL_PARAM_ADD_PATH = "params/text/add.sql";
    protected final String SQL_PARAM_DELETE_PATH = "params/delete.sql";
    protected final String SQL_PARAM_FIND_PATH = "params/text/find.sql";
    protected final String SQL_PARAM_UPDATE_PATH = "params/text/update.sql";

    protected final String SQL_REF_ADD_PATH = "refs/add.sql";
    protected final String SQL_REF_DELETE_ONE_PATH = "refs/deleteOne.sql";
    protected final String SQL_REF_DELETE_ALL_PATH = "refs/deleteAll.sql";
    protected final String SQL_REF_FIND_PATH = "refs/find.sql";
    protected final String SQL_REF_UPDATE_PATH = "refs/update.sql";

    //column names
    protected final String TYPE_ID = "type_id";
    protected final String TYPE_NAME = "type_name";
    protected final String TYPE_DESC = "type_desc";
    protected final String PARENT_TYPE_ID = "parent_type_id";

    protected final String ATTR_ID = "attr_id";
    protected final String ATTR_TYPE_ID = "attr_type_id";
    protected final String ATTR_NAME = "attr_name";
    protected final String MULTIPLE = "multiple";

    protected final String OBJECT_ID = "object_id";
    protected final String OBJECT_VERSION = "object_version";
    protected final String OBJECT_NAME = "object_name";
    protected final String OBJECT_DESC = "object_desc";
    protected final String PARENT_OBJECT_ID = "parent_object_id";

    protected final String NUMBER_VAL = "number_val";
    protected final String TEXT_VAL = "text_val";
    protected final String DATE_VAL = "date_val";
    protected final String BOOLEAN_VAL = "boolean_val";

    protected final String REF_OBJECT_ID = "ref_object_id";

    protected final String ROLE_ID = "role_id";
    protected final String ROLE_NAME = "role_name";
    protected final String ROLE_DESC = "role_desc";

    protected final String READ = "read";
    protected final String WRITE = "write";

    protected abstract T mapObject(MetamodelObject object);

    protected abstract T mapParams(Param param, T entity);

    protected abstract T mapRefs(Ref ref, T entity);

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

    protected int[] executeBatchUpdate(String sql, List<Object[]> params) {
        return jdbcTemplate.batchUpdate(sql, params);
    }

    protected <E> E findOne(String sql, RowMapper<E> rowMapper, Object... params) {
        List<E> query = jdbcTemplate.query(sql, rowMapper, params);
        switch (query.size()) {
            case 0:
                return null;
            case 1:
                return query.get(0);
            default:
                throw new IncorrectResultSizeDataAccessException(1);
        }
    }

    //TODO remake when it will be needed
    protected <E> List<E> findMultiple(String sql, RowMapper<E> rowMapper, Object... params) {
        return jdbcTemplate.query(sql, rowMapper, params);
    }

    protected Long generateId(Integer typeId) {
        Random random = new Random(System.currentTimeMillis());
        String id = typeId.toString().concat(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString()
                        .replaceAll("-", "")
                        .replaceAll("T", "")
                        .replaceAll(":", "")
        ).concat(String.valueOf(random.nextInt(MAX_EXCLUSIVE_BOUND - MIN_INCLUSIVE_BOUND) + MIN_INCLUSIVE_BOUND));
        return Long.valueOf(id);
    }

//    @NoArgsConstructor(access = PUBLIC)
//    protected class ObjectMapRowMapper implements RowMapper<Map<String, ?>> {
//        @Override
//        public Map<String, ?> mapRow(ResultSet rs, int rowNum) throws SQLException {
//            Map<String, Object> row = new HashMap<>();
//            row.put(OBJECT_ID, rs.getLong(OBJECT_ID));
//            row.put(OBJECT_VERSION, rs.getLong(OBJECT_VERSION));
//            row.put(OBJECT_NAME, rs.getString(OBJECT_NAME));
//            row.put(OBJECT_DESC, rs.getString(OBJECT_DESC));
//            return row;
//        }
//    }

    @NoArgsConstructor(access = PUBLIC)
    protected class ObjectRowMapper implements RowMapper<MetamodelObject> {
        @Override
        public MetamodelObject mapRow(ResultSet rs, int rowNum) throws SQLException {
            MetamodelObject object = new MetamodelObject();
            object.setId(rs.getLong(OBJECT_ID));
            object.setVersion(rs.getLong(OBJECT_VERSION));
            object.setName(rs.getString(OBJECT_NAME));
            object.setDesc(rs.getString(OBJECT_DESC));
            return object;
        }
    }

    @NoArgsConstructor(access = PUBLIC)
    protected class RefRowMapper implements RowMapper<Ref> {
        @Override
        public Ref mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ref ref = new Ref();
            ref.setObjectId(rs.getLong(OBJECT_ID));
            ref.setAttrId(rs.getLong(ATTR_ID));
            ref.setRefId(rs.getLong(REF_OBJECT_ID));
            return ref;
        }
    }

    @NoArgsConstructor(access = PUBLIC)
    protected class ParamRowMapper implements RowMapper<Param> {
        @Override
        public Param mapRow(ResultSet rs, int rowNum) throws SQLException {
            Param param = new Param();
            param.setObjectId(rs.getLong(OBJECT_ID));
            param.setAttrId(rs.getLong(ATTR_ID));
            param.setBooleanVal(rs.getBoolean(BOOLEAN_VAL));
            param.setDateVal(OffsetDateTime.ofInstant(rs.getTimestamp(DATE_VAL).toInstant(), ZoneId.systemDefault()));
            param.setNumberVal(rs.getLong(NUMBER_VAL));
            param.setTextVal(rs.getString(TEXT_VAL));
            return param;
        }
    }
}
