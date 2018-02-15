package com.nc.kpi.persistence.metamodel.rows;

import lombok.Data;

@Data
public class RefRow {
    private Long objectId;
    private Long attrId;
    private Long refId;
}
