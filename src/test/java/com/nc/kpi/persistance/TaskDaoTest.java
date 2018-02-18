package com.nc.kpi.persistance;

import com.nc.kpi.config.AppConfig;
import com.nc.kpi.entities.Project;
import com.nc.kpi.entities.Qualification;
import com.nc.kpi.entities.Sprint;
import com.nc.kpi.entities.Task;
import com.nc.kpi.persistence.ProjectDao;
import com.nc.kpi.persistence.QualificationDao;
import com.nc.kpi.persistence.SprintDao;
import com.nc.kpi.persistence.TaskDao;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
@NoArgsConstructor
public class TaskDaoTest extends CrudDaoTest<Task> {
    @Setter
    @Autowired
    private TaskDao taskDao;
    @Setter
    @Autowired
    private SprintDao sprintDao;
    @Setter
    @Autowired
    private ProjectDao projectDao;
    @Setter
    @Autowired
    private QualificationDao qualificationDao;

    private Project project;
    private Sprint sprint;

    private final long juniorId = 2;
    private final long middleId = 3;
    private final long seniorId = 4;

    private Qualification junior;
    private Qualification middle;
    private Qualification senior;

    private OffsetDateTime forAddEstimate;
    private OffsetDateTime forFindEstimate;
    private OffsetDateTime forUpdateEstimate;
    private OffsetDateTime forDeleteEstimate;

    private OffsetDateTime forAddActual;
    private OffsetDateTime forFindActual;
    private OffsetDateTime forUpdateActual;
    private OffsetDateTime forDeleteActual;

    private Duration forAddOvertime;
    private Duration forFindOvertime;
    private Duration forUpdateOvertime;
    private Duration forDeleteOvertime;

    @Override
    public void before() {
        initProject();
        insertProject();
        initSprint();
        insertSprint();
        initQualifications();
        initEstimate();
        initActual();
        initOvertime();
        initTasks();
        insertTasks();
    }

    private void initActual() {
        forAddActual = OffsetDateTime.parse("2015-07-07T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        forFindActual = OffsetDateTime.parse("2017-07-07T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        forUpdateActual = OffsetDateTime.parse("2016-07-29T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        forDeleteActual = OffsetDateTime.parse("2017-12-07T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    private void insertSprint() {
        sprintDao.add(sprint);
        sprint.setName(null);
        sprint.setActive(null);
        sprint.setProject(null);
        sprint.setVersion(null);
    }

    private void insertProject() {
        projectDao.add(project);
        project.setName(null);
        project.setVersion(null);
        project.setActive(null);
        project.setStartDate(null);
        project.setEndDate(null);
    }

    private void initProject() {
        project = new Project();
        project.setName("test project for tasks");
        project.setActive(true);
        project.setStartDate(OffsetDateTime.parse("2013-07-07T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        project.setEndDate(OffsetDateTime.parse("2015-09-08T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    private void initSprint() {
        sprint = new Sprint();
        sprint.setName("test sprint for tasks");
        sprint.setActive(true);
        sprint.setProject(project);
    }

    private void insertTasks() {
        taskDao.add(forFind);
        taskDao.add(forUpdate);
        taskDao.add(forDelete);
    }

    private void initOvertime() {
        forAddOvertime = Duration.ofDays(29);
        forFindOvertime = Duration.ofDays(1);
        forUpdateOvertime = Duration.ofHours(22);
        forDeleteOvertime = Duration.ofHours(20);
    }

    private void initEstimate() {
        forAddEstimate = OffsetDateTime.parse("2012-07-07T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        forFindEstimate = OffsetDateTime.parse("2011-07-13T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        forUpdateEstimate = OffsetDateTime.parse("2013-07-12T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        forDeleteEstimate = OffsetDateTime.parse("2014-07-07T21:36:10.598+03:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    private void initQualifications() {
        junior = qualificationDao.find(juniorId);
        middle = qualificationDao.find(middleId);
        senior = qualificationDao.find(seniorId);
        junior.setName(null);
        junior.setDesc(null);
        middle.setName(null);
        middle.setDesc(null);
        senior.setName(null);
        senior.setDesc(null);
    }

    private void initTasks() {
        forAdd = new Task();
        forFind = new Task();
        forUpdate = new Task();
        forDelete = new Task();
        forAdd.setName(FOR_ADD_NAME);
        forAdd.setActive(true);
        forAdd.setQualification(middle);
        forAdd.setEstimate(forAddEstimate);
        forAdd.setActual(forAddActual);
        forAdd.setOvertime(forAddOvertime);
        forAdd.setSprint(sprint);
        forFind.setName(FOR_FIND_NAME);
        forFind.setActive(true);
        forFind.setQualification(junior);
        forFind.setEstimate(forFindEstimate);
        forFind.setActual(forFindActual);
        forFind.setOvertime(forFindOvertime);
        forFind.setSprint(sprint);
        forUpdate.setName(FOR_UPDATE_NAME);
        forUpdate.setActive(false);
        forUpdate.setQualification(senior);
        forUpdate.setEstimate(forUpdateEstimate);
        forUpdate.setActual(forUpdateActual);
        forUpdate.setOvertime(forUpdateOvertime);
        forUpdate.setSprint(sprint);
        forDelete.setName(FOR_DELETE_NAME);
        forDelete.setActive(false);
        forDelete.setQualification(middle);
        forDelete.setEstimate(forDeleteEstimate);
        forDelete.setActual(forDeleteActual);
        forDelete.setOvertime(forDeleteOvertime);
        forDelete.setSprint(sprint);
    }

    @Override
    public void after() {
        if (forAdd.getId() != null) taskDao.delete(forAdd.getId());
        taskDao.delete(forFind.getId());
        taskDao.delete(forUpdate.getId());
        taskDao.delete(forDelete.getId());
        sprintDao.delete(sprint.getId());
        projectDao.delete(project.getId());
    }

    @Override
    public void add() {
        Task expected = forAdd;
        taskDao.add(forAdd);
        Task actual = taskDao.find(forAdd.getId());
        Assert.assertEquals(expected, actual);
    }

    @Override
    public void delete() {
        taskDao.delete(forDelete.getId());
        Assert.assertNull(taskDao.find(forDelete.getId()));
    }

    @Override
    public void find() {
        Task expected = forFind;
        Task actual = taskDao.find(forFind.getId());
        Assert.assertEquals(expected, actual);
    }

    @Override
    public void update() {
        forUpdate.setQualification(junior);
        forUpdate.setEstimate(forFindEstimate);
        forUpdate.setOvertime(forFindOvertime);
        Task expected = forUpdate;
        taskDao.update(forUpdate);
        Task actual = taskDao.find(forUpdate.getId());
        Assert.assertEquals(expected, actual);
    }
}
