package com.nc.kpi.entities;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private Long id;
    private Long version;
    private String name;
    private String desc;
    private String bio;
    private Qualification qualification;
    private List<Role> roles;
    private List<Project> managedProjects;
    private List<Project> orderedProjects;
    private List<Task> tasks;
}
