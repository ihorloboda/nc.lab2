package com.nc.kpi.persistence.metamodel.rows;

import lombok.Data;

import java.time.Duration;
import java.time.OffsetDateTime;

@Data
public class ParamRow {
    private Long objectId;
    private Long attrId;
    private Long numberVal;
    private String textVal;
    private OffsetDateTime dateVal;
    private Duration intervalVal;
    private Boolean booleanVal;
}
