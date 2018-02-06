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
