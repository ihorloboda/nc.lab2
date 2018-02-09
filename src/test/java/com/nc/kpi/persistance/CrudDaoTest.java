package com.nc.kpi.persistance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class CrudDaoTest<T> {
    //entities for tests
    protected T forAdd;
    protected T forFind;
    protected T forUpdate;
    protected T forDelete;

    protected final String FOR_ADD_NAME = "for add name";
    protected final String FOR_FIND_NAME = "for find name";
    protected final String FOR_UPDATE_NAME = "for update name";
    protected final String FOR_DELETE_NAME = "for delete name";

    protected final String FOR_ADD_DESC = "for add desc";
    protected final String FOR_FIND_DESC = "for find desc";
    protected final String FOR_UPDATE_DESC = "for update desc";
    protected final String FOR_DELETE_DESC = "for delete desc";

    protected final long JUNIOR_QUALIFICATION_ID = 2;
    protected final long MIDDLE_QUALIFICATION_ID = 3;
    protected final long SENIOR_QUALIFICATION_ID = 4;

    protected final long TEST_ROLE_1_ID = 11;
    protected final long TEST_ROLE_2_ID = 12;
    protected final long TEST_ROLE_3_ID = 13;

    @Before
    public abstract void before();

    @After
    public abstract void after();

    @Test
    public abstract void add();

    @Test
    public abstract void delete();

    @Test
    public abstract void find();

    @Test
    public abstract void update();
}
