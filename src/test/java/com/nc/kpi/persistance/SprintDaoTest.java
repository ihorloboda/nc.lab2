package com.nc.kpi.persistance;

import com.nc.kpi.config.AppConfig;
import com.nc.kpi.entities.Project;
import com.nc.kpi.entities.Sprint;
import com.nc.kpi.persistence.ProjectDao;
import com.nc.kpi.persistence.SprintDao;
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
public class SprintDaoTest extends CrudDaoTest<Sprint> {
    @Setter
    @Autowired
    private SprintDao sprintDao;
    @Setter
    @Autowired
    private ProjectDao projectDao;

    private Project project;

    @Override
    public void before() {
        initProject();
        insertProject();
        initSprints();
        insertSprints();
    }

    private void insertSprints() {
        sprintDao.add(forFind);
        sprintDao.add(forUpdate);
        sprintDao.add(forDelete);
    }

    private void initSprints() {
        forAdd = new Sprint();
        forFind = new Sprint();
        forUpdate = new Sprint();
        forDelete = new Sprint();
        forAdd.setName(FOR_ADD_NAME);
        forAdd.setDesc(FOR_ADD_DESC);
        forAdd.setActive(true);
        forAdd.setProject(project);
        forFind.setName(FOR_FIND_NAME);
        forFind.setDesc(FOR_FIND_DESC);
        forFind.setActive(true);
        forFind.setProject(project);
        forUpdate.setName(FOR_UPDATE_NAME);
        forUpdate.setDesc(FOR_UPDATE_DESC);
        forUpdate.setActive(true);
        forUpdate.setProject(project);
        forDelete.setName(FOR_DELETE_NAME);
        forDelete.setDesc(FOR_DELETE_DESC);
        forDelete.setActive(true);
        forDelete.setProject(project);
    }

    private void insertProject() {
        projectDao.add(project);
        project.setName(null);
        project.setActive(null);
        project.setStartDate(null);
        project.setEndDate(null);
        project.setVersion(null);
    }

    private void initProject() {
        project = new Project();
        project.setName("test project for sprints");
        project.setActive(true);
        project.setStartDate(OffsetDateTime.parse("2012-07-07T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        project.setEndDate(OffsetDateTime.parse("2017-09-08T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }


    @Override
    public void after() {
        if (forAdd.getId() != null) sprintDao.delete(forAdd.getId());
        sprintDao.delete(forFind.getId());
        sprintDao.delete(forUpdate.getId());
        sprintDao.delete(forDelete.getId());
        projectDao.delete(project.getId());
    }

    @Override
    public void add() {
        Sprint expected = forAdd;
        sprintDao.add(forAdd);
        Sprint actual = sprintDao.find(forAdd.getId());
        Assert.assertEquals(expected, actual);
    }

    @Override
    public void delete() {
        sprintDao.delete(forDelete.getId());
        Assert.assertNull(sprintDao.find(forDelete.getId()));
    }

    @Override
    public void find() {
        Sprint expected = forFind;
        Sprint actual = sprintDao.find(forFind.getId());
        Assert.assertEquals(expected, actual);
    }

    @Override
    public void update() {
        forUpdate.setActive(false);
        forUpdate.setName("updated name");
        sprintDao.update(forUpdate);
        Sprint expected = forUpdate;
        Sprint actual = sprintDao.find(forUpdate.getId());
        Assert.assertEquals(expected, actual);
    }
}
