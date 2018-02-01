package com.nc.kpi.entities;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class Project {
    private Long id;
    private String name;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private Boolean active;
    private User manager;
    private User customer;
    private List<Sprint> sprints;
}
