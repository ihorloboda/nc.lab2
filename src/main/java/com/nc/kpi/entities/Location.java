package com.nc.kpi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Location {
    private Long id;
    private String country;
    private String city;
    private List<Office> offices;
}
