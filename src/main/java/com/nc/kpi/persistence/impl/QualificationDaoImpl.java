package com.nc.kpi.persistence.impl;

import com.nc.kpi.entities.Qualification;
import com.nc.kpi.persistence.QualificationDao;
import com.nc.kpi.persistence.AbstractDao;
import com.nc.kpi.persistence.metamodel.consts.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class QualificationDaoImpl extends AbstractDao<Qualification> implements QualificationDao {
    private final String SQL_FIND_PATH = "qualifications/find.sql";
    private final String SQL_ADD_PATH = "qualifications/add.sql";
    private final String SQL_UPDATE_PATH = "qualifications/update.sql";
    private final String SQL_DELETE_PATH = "qualifications/delete.sql";

    @Autowired
    public QualificationDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Qualification find(Long id) {
        String sql = loadSqlStatement(SQL_FIND_PATH);
        return findOne(sql, new QualificationRowMapper(), id);
    }

    @Override
    public void add(Qualification entity) {
        String sql = loadSqlStatement(SQL_ADD_PATH);
        entity.setId(generateId(Types.QUALIFICATION));
        executeUpdate(sql, entity.getId(), Types.QUALIFICATION, entity.getName(), entity.getDesc());
    }

    @Override
    public void update(Qualification entity) {
        String sql = loadSqlStatement(SQL_UPDATE_PATH);
        executeUpdate(sql, entity.getName(), entity.getDesc(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = loadSqlStatement(SQL_DELETE_PATH);
        executeUpdate(sql, id);
    }

    private class QualificationRowMapper implements RowMapper<Qualification> {
        @Override
        public Qualification mapRow(ResultSet rs, int rowNum) throws SQLException {
            Qualification qualification = new Qualification();
            qualification.setId(rs.getLong("object_id"));
            qualification.setName(rs.getString("object_name"));
            qualification.setDesc(rs.getString("object_desc"));
            return qualification;
        }
    }
}
