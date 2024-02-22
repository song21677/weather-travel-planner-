package com.olaenmanijo.weatherbasedtravelplanner.tourapi.domain;

import java.util.List;
import com.olaenmanijo.weatherbasedtravelplanner.plan_after.Links;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceResponse {
    private long id;
    private String category;
    private String name;
    private String roadNameAdr;
    private String area;
    private String sigungu;
    private String zipCode;
    private double longitude;
    private double latitude;
    private String createTime;
    private String modifiedTime;
    private List<Links> links;
}
