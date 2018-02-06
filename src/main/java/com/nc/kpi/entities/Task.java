package com.nc.kpi.entities;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class Task {
    private Long id;
    private Long version;
    private String name;
    private String desc;
    private Sprint sprint;
    //TODO make these fields as time intervals
    private OffsetDateTime estimate;
    private OffsetDateTime actual;
    private OffsetDateTime overtime;
    private Boolean active;
    private Qualification qualification;
    //TODO decide what structure is needed
    private List<Task> subtasks;
    private List<Task> prevTasks;
    private List<User> users;
}
