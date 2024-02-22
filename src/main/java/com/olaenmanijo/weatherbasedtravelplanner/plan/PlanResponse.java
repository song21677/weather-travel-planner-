package com.olaenmanijo.weatherbasedtravelplanner.plan_after;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanResponse {

    private long id;
    private String title;
    private String startDate;
    private String endDate;
    private List<Links> links;
}
