package com.olaenmanijo.weatherbasedtravelplanner.plan_after;

import java.util.List;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class DetailPlanDTO {
    private String date;
    private String startHour;
    private String endHour;
    private String placeId;
    private String placeColor;
    
    
    public DetailPlanDTO() {   
        
    }
    
    public DetailPlanDTO(String date, String startHour, String endHour, String placeId, String placeColor) {
         super();
         this.date = date;
         this.startHour = startHour;
         this.endHour = endHour;
         this.placeId = placeId;
         this.placeColor = placeColor;
     }

    @Override
    public String toString() {
        return "DetailPlan [date=" + date + ", startHour=" + startHour + ", endHour=" + endHour
                + ", placeId=" + placeId + ", placeColor=" + placeColor + "]";
    }
   

//  { "plan" :
//  { 
//      "startDate" : "20240107",
//      "endDate" : "20240110",
//      "title" : "첫 일본 여행",
//      "places" : [
//          {
//              "date" : "0",
//              "startHour" : "07",
//              "endHour" : "08",
//              "placeNo" : "1",
//              "placeColor" : "red"
//          },
//          {
//              "date" : "1",
//              "startHour" : "07",
//              "endHour" : "08",
//              "placeNo" : "2",
//              "placeColor" : "green"
//          },
//          ...
//      ]
//  }
//}
}
