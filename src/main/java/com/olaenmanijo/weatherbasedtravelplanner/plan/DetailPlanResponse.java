package com.olaenmanijo.weatherbasedtravelplanner.plan_after;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailPlanResponse {
    
    private long id;
    private long placeId;
    private String date;
    private String startHour;
    private String endHour;
    private List<Links> links;
    
    @Override
    public String toString() {
        return "DetailPlanResponse [id=" + id + ", placeId=" + placeId + ", date=" + date
                + ", startHour=" + startHour + ", endHour=" + endHour + "]";
    }
}
