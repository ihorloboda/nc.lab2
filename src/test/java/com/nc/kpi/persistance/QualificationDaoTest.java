package com.nc.kpi.persistance;

import com.nc.kpi.config.AppConfig;
import com.nc.kpi.config.DBConfig;
import com.nc.kpi.entities.Qualification;
import com.nc.kpi.persistence.QualificationDao;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DBConfig.class, AppConfig.class}, loader = AnnotationConfigContextLoader.class)
@NoArgsConstructor
public class QualificationDaoTest extends CrudDaoTest {
    private Qualification q1;
    private Qualification q2;

    @Autowired
    @Setter
    private QualificationDao qualificationDao;

    @Before
    public void before() {
        final String TEST_NAME_1 = "test name 1";
        final String TEST_DESC_1 = "test desc 1";
        final String TEST_NAME_2 = "test name 2";
        final String TEST_DESC_2 = "test desc 2";
        q1 = new Qualification();
        q1.setName(TEST_NAME_1);
        q1.setDesc(TEST_DESC_1);
        q2 = new Qualification();
        q2.setName(TEST_NAME_2);
        q2.setName(TEST_DESC_2);
        qualificationDao.add(q1);
    }

    @After
    public void after() {
        qualificationDao.delete(q1.getId());
        qualificationDao.delete(q2.getId());
    }

    @Override
    @Test
    public void find() {
        Qualification expected = q1;
        Qualification actual = qualificationDao.find(q1.getId());
        Assert.assertEquals(expected, actual);
    }

    @Override
    public void add() {
        Qualification expected = q2;
        qualificationDao.add(expected);
        Qualification actual = qualificationDao.find(expected.getId());
        Assert.assertEquals(expected, actual);
    }

    @Override
    public void delete() {
        qualificationDao.delete(q2.getId());
        Assert.assertNull(qualificationDao.find(q2.getId()));
    }

    @Override
    public void update() {
        final String TEST_NAME = "updated name";
        q1.setName(TEST_NAME);
        Qualification expected = q1;
        qualificationDao.update(q1);
        Qualification actual = qualificationDao.find(q1.getId());
        Assert.assertEquals(expected, actual);
    }
}
