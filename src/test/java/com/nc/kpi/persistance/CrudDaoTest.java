package com.nc.kpi.persistance;

import org.junit.Test;


public abstract class CrudDaoTest {
    @Test
    public abstract void add();

    @Test
    public abstract void delete();

    @Test
    public abstract void find();

    @Test
    public abstract void update();
}
