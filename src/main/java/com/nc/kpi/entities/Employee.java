package com.nc.kpi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Employee {
    private Long id;
    private String name;
    private String bio;
    private OffsetDateTime hireDate;
    private OffsetDateTime fireDate;
    private List<Role> roles;
}
