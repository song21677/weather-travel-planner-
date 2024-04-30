package com.olaenmanijo.weatherbasedtravelplanner.plan_after;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailPlan {
    private long id;
    private long planId;
    private long placeId;
    private String date;
    private String startHour;
    private String endHour;
    
    public DetailPlan(long planId, long placeId, String date, String startHour, String endHour) {
        super();
        this.planId = planId;
        this.placeId = placeId;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
    }
}
