package com.nc.kpi.persistance;

import com.nc.kpi.config.AppConfig;
import com.nc.kpi.entities.Role;
import com.nc.kpi.persistence.RoleDao;
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
public class RoleDaoTest extends CrudDaoTest<Role> {
    @Autowired
    @Setter
    private RoleDao roleDao;

    @Override
    public void before() {
        forAdd = new Role();
        forFind = new Role();
        forUpdate = new Role();
        forDelete = new Role();
        forAdd.setName(FOR_ADD_NAME);
        forFind.setName(FOR_FIND_NAME);
        forUpdate.setName(FOR_UPDATE_NAME);
        forDelete.setName(FOR_DELETE_NAME);
        forAdd.setDesc(FOR_ADD_DESC);
        forFind.setDesc(FOR_FIND_DESC);
        forUpdate.setDesc(FOR_UPDATE_DESC);
        forDelete.setDesc(FOR_DELETE_DESC);
        roleDao.add(forFind);
        roleDao.add(forUpdate);
        roleDao.add(forDelete);
    }

    @Override
    public void after() {
        if (forAdd.getId() != null) roleDao.delete(forAdd.getId());
        roleDao.delete(forFind.getId());
        roleDao.delete(forUpdate.getId());
        roleDao.delete(forDelete.getId());
    }

    @Override
    public void add() {
        Role expected = forAdd;
        roleDao.add(expected);
        Role actual = roleDao.find(expected.getId());
        Assert.assertEquals(expected, actual);
    }

    @Override
    public void delete() {
        roleDao.delete(forDelete.getId());
        Assert.assertNull(roleDao.find(forDelete.getId()));
    }

    @Override
    public void find() {
        Role expected = forFind;
        Role actual = roleDao.find(forFind.getId());
        Assert.assertEquals(expected, actual);
    }

    @Override
    public void update() {
        final String TEST_NAME = "updated name";
        forUpdate.setName(TEST_NAME);
        Role expected = forUpdate;
        roleDao.update(forUpdate);
        Role actual = roleDao.find(forUpdate.getId());
        Assert.assertEquals(expected, actual);
    }
}
