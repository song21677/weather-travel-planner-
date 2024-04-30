package com.olaenmanijo.weatherbasedtravelplanner.plan_after;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@ToString
public class Plan2 {

    private long id;
    private long memberId;
    private String title;
    private String startDate;
    private String endDate;
    
    public Plan2(long memberId, String title, String startDate, String endDate) {
        super();
        this.memberId = memberId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Plan2 [id=" + id + ", memberId=" + memberId + ", title=" + title + ", startDate="
                + startDate + ", endDate=" + endDate + "]";
    }
    
}
