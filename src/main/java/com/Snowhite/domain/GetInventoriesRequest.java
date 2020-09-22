package com.Snowhite.domain;

import lombok.Data;
import java.util.Date;

@Data
public class GetInventoriesRequest {

    private Status status;

    private Integer page = 1;

    private  String sortColumn = "id";

    private  String sortDirection = "asc";

    private Date startDate;

    private Date endDate;
}
