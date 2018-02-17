package com.nc.kpi.persistance;

import com.nc.kpi.config.AppConfig;
import com.nc.kpi.entities.Project;
import com.nc.kpi.entities.User;
import com.nc.kpi.persistence.ProjectDao;
import com.nc.kpi.persistence.UserDao;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
@NoArgsConstructor
public class ProjectDaoTest extends CrudDaoTest<Project> {
    @Setter
    @Autowired
    private ProjectDao projectDao;
    @Setter
    @Autowired
    private UserDao userDao;

    private User forAddManager;
    private User forFindManager;
    private User forUpdateManager;
    private User forDeleteManager;

    private User forAddCustomer;
    private User forFindCustomer;
    private User forUpdateCustomer;
    private User forDeleteCustomer;

    private OffsetDateTime forAddStartDate;
    private OffsetDateTime forFindStartDate;
    private OffsetDateTime forUpdateStartDate;
    private OffsetDateTime forDeleteStartDate;

    private OffsetDateTime forAddEndDate;
    private OffsetDateTime forFindEndDate;
    private OffsetDateTime forUpdateEndDate;
    private OffsetDateTime forDeleteEndDate;

    @Override
    public void before() {
        initDates();
        initUsers();
        initProjects();
        insertUsers();
        insertProjects();
    }

    private void insertProjects() {
        projectDao.add(forFind);
        projectDao.add(forUpdate);
        projectDao.add(forDelete);
    }

    private void insertUsers() {
        userDao.add(forAddCustomer);
        userDao.add(forFindCustomer);
        userDao.add(forUpdateCustomer);
        userDao.add(forDeleteCustomer);
        userDao.add(forAddManager);
        userDao.add(forFindManager);
        userDao.add(forUpdateManager);
        userDao.add(forDeleteManager);
        forAddCustomer.setName(null);
        forFindCustomer.setName(null);
        forUpdateCustomer.setName(null);
        forDeleteCustomer.setName(null);
        forAddManager.setName(null);
        forFindManager.setName(null);
        forUpdateManager.setName(null);
        forDeleteManager.setName(null);
        forAddCustomer.setVersion(null);
        forFindCustomer.setVersion(null);
        forUpdateCustomer.setVersion(null);
        forDeleteCustomer.setVersion(null);
        forAddManager.setVersion(null);
        forFindManager.setVersion(null);
        forUpdateManager.setVersion(null);
        forDeleteManager.setVersion(null);

    }

    private void initDates() {
        forAddStartDate = OffsetDateTime.parse("2012-07-07T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        forFindStartDate = OffsetDateTime.parse("2011-07-13T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        forUpdateStartDate = OffsetDateTime.parse("2013-07-12T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        forDeleteStartDate = OffsetDateTime.parse("2014-07-07T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        forAddEndDate = OffsetDateTime.parse("2015-07-07T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        forFindEndDate = OffsetDateTime.parse("2017-07-07T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        forUpdateEndDate = OffsetDateTime.parse("2016-07-29T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        forDeleteEndDate = OffsetDateTime.parse("2017-12-07T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    private void initUsers() {
        forAddCustomer = new User();
        forFindCustomer = new User();
        forUpdateCustomer = new User();
        forDeleteCustomer = new User();
        forAddManager = new User();
        forFindManager = new User();
        forUpdateManager = new User();
        forDeleteManager = new User();
        forAddCustomer.setName(FOR_ADD_NAME);
        forFindCustomer.setName(FOR_FIND_NAME);
        forUpdateCustomer.setName(FOR_UPDATE_NAME);
        forDeleteCustomer.setName(FOR_DELETE_NAME);
        forAddManager.setName(FOR_ADD_NAME);
        forFindManager.setName(FOR_FIND_NAME);
        forUpdateManager.setName(FOR_UPDATE_NAME);
        forDeleteManager.setName(FOR_DELETE_NAME);
    }

    private void initProjects() {
        forAdd = new Project();
        forFind = new Project();
        forUpdate = new Project();
        forDelete = new Project();
        forAdd.setName(FOR_ADD_NAME);
        forAdd.setActive(true);
        forAdd.setStartDate(forAddStartDate);
        forAdd.setEndDate(forAddEndDate);
        forAdd.setCustomer(forAddCustomer);
        forAdd.setManager(forAddManager);
        forFind.setName(FOR_FIND_NAME);
        forFind.setActive(true);
        forFind.setStartDate(forFindStartDate);
        forFind.setEndDate(forFindEndDate);
        forFind.setCustomer(forFindCustomer);
        forFind.setManager(forFindManager);
        forUpdate.setName(FOR_UPDATE_NAME);
        forUpdate.setActive(false);
        forUpdate.setStartDate(forUpdateStartDate);
        forUpdate.setEndDate(forUpdateEndDate);
        forUpdate.setCustomer(forUpdateCustomer);
        forUpdate.setManager(forUpdateManager);
        forDelete.setName(FOR_DELETE_NAME);
        forDelete.setActive(false);
        forDelete.setStartDate(forDeleteStartDate);
        forDelete.setEndDate(forDeleteEndDate);
        forDelete.setCustomer(forDeleteCustomer);
        forDelete.setManager(forDeleteManager);
    }

    @Override
    public void after() {
        deleteProjects();
        deleteUsers();
    }

    private void deleteUsers() {
        userDao.delete(forAddCustomer.getId());
        userDao.delete(forFindCustomer.getId());
        userDao.delete(forUpdateCustomer.getId());
        userDao.delete(forDeleteCustomer.getId());
        userDao.delete(forAddManager.getId());
        userDao.delete(forFindManager.getId());
        userDao.delete(forUpdateManager.getId());
        userDao.delete(forDeleteManager.getId());
    }

    private void deleteProjects() {
        if (forAdd.getId() != null) projectDao.delete(forAdd.getId());
        projectDao.delete(forFind.getId());
        projectDao.delete(forUpdate.getId());
        projectDao.delete(forDelete.getId());
    }

    @Override
    public void add() {
        Project expected = forAdd;
        projectDao.add(forAdd);
        Project actual = projectDao.find(forAdd.getId());
        Assert.assertEquals(expected, actual);
    }

    @Override
    public void delete() {
        projectDao.delete(forDelete.getId());
        Assert.assertNull(projectDao.find(forDelete.getId()));
    }

    @Override
    public void find() {
        Project expected = forFind;
        Project actual = projectDao.find(forFind.getId());
        Assert.assertEquals(expected, actual);
    }

    @Override
    public void update() {
        forUpdate.setActive(!forUpdate.getActive());
        forUpdate.setManager(forFindManager);
        forUpdate.setCustomer(forFindCustomer);
        forUpdate.setStartDate(forFindStartDate);
        forUpdate.setEndDate(forFindEndDate);
        Project expected = forUpdate;
        projectDao.update(forUpdate);
        Project actual = projectDao.find(forUpdate.getId());
        Assert.assertEquals(expected, actual);
    }
}
