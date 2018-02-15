package com.nc.kpi.persistence.metamodel.rows;

import lombok.Data;

@Data
public class ObjectRow {
    private Long id;
    private Long version;
    private String name;
    private String desc;
}
