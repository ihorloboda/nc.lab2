package com.nc.kpi.persistence.metamodel.rows;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Param {
    private Long objectId;
    private Long attrId;
    private Long numberVal;
    private String textVal;
    private OffsetDateTime dateVal;
    private Boolean booleanVal;
}
