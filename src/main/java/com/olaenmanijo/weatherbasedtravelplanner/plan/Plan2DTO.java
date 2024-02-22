package com.olaenmanijo.weatherbasedtravelplanner.plan_after;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;
import com.olaenmanijo.weatherbasedtravelplanner.plan.PlanDTO2;
import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//@Component
@Getter
//@AllArgsConstructor
public class Plan2DTO {
    
    private String title;
    @NotBlank
    private String startDate;
    @NotBlank
    private String endDate;
    private List<DetailPlanDTO> detailPlans = new ArrayList<>();
    
    public Plan2DTO() {
        
    }

    @Override
    public String toString() {
        return "PlanForm [title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
                + ", detailPlans=" + detailPlans + "]";
    }  
    
    
//    public void add(PlanDTO2 place) {
//        places.add(place);
//    }
//    
//    public void sortPlaces() {
//        places.sort(Comparator
//                .comparing(PlanDTO2::getDate)
//                .thenComparing(PlanDTO2::getStartHour));
//    }

//    { "plan" :
//    { 
//        "startDate" : "20240107",
//        "endDate" : "20240110",
//        "title" : "첫 일본 여행",
//        "places" : [
//            {
//                "date" : "0",
//                "startHour" : "07",
//                "endHour" : "08",
//                "placeNo" : "1",
//                "placeColor" : "red"
//            },
//            {
//                "date" : "1",
//                "startHour" : "07",
//                "endHour" : "08",
//                "placeNo" : "2",
//                "placeColor" : "green"
//            },
//            ...
//        ]
//    }
//}
}
