package com.nc.kpi.entities;

import lombok.Data;

@Data
public class Grant<T> {
    private Long id;
    private Class<T> type;
    private T object;
    private Boolean read;
    private Boolean write;
}
