package com.nc.kpi.persistance;

import com.nc.kpi.config.AppConfig;
import com.nc.kpi.entities.Qualification;
import com.nc.kpi.entities.Role;
import com.nc.kpi.entities.User;
import com.nc.kpi.persistence.UserDao;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;

//TODO check null results
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
@NoArgsConstructor
public class UserDaoTest extends CrudDaoTest<User> {
    @Autowired
    @Setter
    private UserDao userDao;

    private Qualification q1;
    private Qualification q2;
    private Qualification q3;

    private Role r1;
    private Role r2;
    private Role r3;

    private List<Role> rolesForAdd;
    private List<Role> rolesForFind;
    private List<Role> rolesForUpdate;
    private List<Role> rolesForDelete;

    private final String BIO = "BIO";

    @Override
    public void before() {
        initQualifications();
        initRoles();
        initUsers();
        insertUsers();
    }

    private void initQualifications() {
        q1 = new Qualification();
        q2 = new Qualification();
        q3 = new Qualification();
        q1.setId(JUNIOR_QUALIFICATION_ID);
        q2.setId(MIDDLE_QUALIFICATION_ID);
        q3.setId(SENIOR_QUALIFICATION_ID);
    }

    private void initRoles() {
        r1 = new Role();
        r2 = new Role();
        r3 = new Role();
        r1.setId(TEST_ROLE_1_ID);
        r2.setId(TEST_ROLE_2_ID);
        r3.setId(TEST_ROLE_3_ID);
        rolesForAdd = new ArrayList<>();
        rolesForFind = new ArrayList<>();
        rolesForUpdate = new ArrayList<>();
        rolesForDelete = new ArrayList<>();
        rolesForAdd.add(r1);
        rolesForAdd.add(r2);
        rolesForFind.add(r1);
        rolesForFind.add(r3);
        rolesForUpdate.add(r1);
        rolesForUpdate.add(r2);
        rolesForDelete.add(r1);
        rolesForDelete.add(r3);
    }

    private void initUsers() {
        forAdd = new User();
        forFind = new User();
        forUpdate = new User();
        forDelete = new User();
        forAdd.setName(FOR_ADD_NAME);
        forFind.setName(FOR_FIND_NAME);
        forUpdate.setName(FOR_UPDATE_NAME);
        forDelete.setName(FOR_DELETE_NAME);
        forAdd.setDesc(FOR_ADD_DESC);
        forFind.setDesc(FOR_FIND_DESC);
        forUpdate.setDesc(FOR_UPDATE_DESC);
        forDelete.setDesc(FOR_DELETE_DESC);
        forAdd.setBio(BIO);
        forFind.setBio(BIO);
        forUpdate.setBio(BIO);
        forDelete.setBio(BIO);
        forAdd.setQualification(q1);
        forFind.setQualification(q2);
        forUpdate.setQualification(q3);
        forDelete.setQualification(q2);
        forAdd.setRoles(rolesForAdd);
        forFind.setRoles(rolesForFind);
        forUpdate.setRoles(rolesForUpdate);
        forDelete.setRoles(rolesForDelete);
    }

    private void insertUsers() {
        userDao.add(forFind);
        userDao.add(forUpdate);
        userDao.add(forDelete);
    }

    @Override
    public void after() {
        userDao.delete(forAdd.getId());
        userDao.delete(forFind.getId());
        userDao.delete(forUpdate.getId());
        userDao.delete(forDelete.getId());
    }

    @Override
    public void add() {
        User expected = forAdd;
        userDao.add(forAdd);
        User actual = userDao.find(forAdd.getId());
        Assert.assertEquals(expected, actual);
    }

    @Override
    public void delete() {
        userDao.delete(forDelete.getId());
        Assert.assertNull(userDao.find(forDelete.getId()));
    }

    @Override
    public void find() {
        User expected = forFind;
        User actual = userDao.find(forFind.getId());
        Assert.assertEquals(expected, actual);
    }

    @Override
    public void update() {
        final String TEST_NAME = "updated name";
        final String TEST_BIO = "updated bio";
        forUpdate.setName(TEST_NAME);
        forUpdate.setBio(TEST_BIO);
        forUpdate.getRoles().remove(r1);
        forUpdate.getRoles().add(r3);
        forUpdate.setQualification(q1);
        User expected = forUpdate;
        userDao.update(expected);
        User actual = userDao.find(forUpdate.getId());
        Assert.assertEquals(expected, actual);
    }
}
