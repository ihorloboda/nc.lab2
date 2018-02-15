package com.nc.kpi.persistance;

import com.nc.kpi.config.AppConfig;
import com.nc.kpi.entities.Qualification;
import com.nc.kpi.persistence.QualificationDao;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
@NoArgsConstructor
public class QualificationDaoTest extends CrudDaoTest<Qualification> {
    @Autowired
    @Setter
    private QualificationDao qualificationDao;

    @Override
    public void before() {
        forAdd = new Qualification();
        forFind = new Qualification();
        forUpdate = new Qualification();
        forDelete = new Qualification();
        forAdd.setName(FOR_ADD_NAME);
        forFind.setName(FOR_FIND_NAME);
        forUpdate.setName(FOR_UPDATE_NAME);
        forDelete.setName(FOR_DELETE_NAME);
        forAdd.setDesc(FOR_ADD_DESC);
        forFind.setDesc(FOR_FIND_DESC);
        forUpdate.setDesc(FOR_UPDATE_DESC);
        forDelete.setDesc(FOR_DELETE_DESC);
        qualificationDao.add(forFind);
        qualificationDao.add(forUpdate);
        qualificationDao.add(forDelete);
    }

    @Override
    public void after() {
        if (forAdd.getId() != null) qualificationDao.delete(forAdd.getId());
        qualificationDao.delete(forFind.getId());
        qualificationDao.delete(forUpdate.getId());
        qualificationDao.delete(forDelete.getId());
    }

    @Override
    public void find() {
        Qualification expected = forFind;
        Qualification actual = qualificationDao.find(forFind.getId());
        Assert.assertEquals(expected, actual);
    }

    @Override
    public void add() {
        Qualification expected = forAdd;
        qualificationDao.add(expected);
        Qualification actual = qualificationDao.find(expected.getId());
        Assert.assertEquals(expected, actual);
    }

    @Override
    public void delete() {
        qualificationDao.delete(forDelete.getId());
        Assert.assertNull(qualificationDao.find(forDelete.getId()));
    }

    @Override
    public void update() {
        final String TEST_NAME = "updated name";
        forUpdate.setName(TEST_NAME);
        Qualification expected = forUpdate;
        qualificationDao.update(forUpdate);
        Qualification actual = qualificationDao.find(forUpdate.getId());
        Assert.assertEquals(expected, actual);
    }
}
