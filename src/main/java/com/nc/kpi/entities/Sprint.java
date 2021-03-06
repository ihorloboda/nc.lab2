package com.nc.kpi.entities;

import lombok.Data;

import java.util.List;

@Data
public class Sprint {
    private Long id;
    private Long version;
    private String name;
    private String desc;
    private Sprint prevSprint;
    private Project project;
    private Boolean active;
    private List<Task> tasks;
}
