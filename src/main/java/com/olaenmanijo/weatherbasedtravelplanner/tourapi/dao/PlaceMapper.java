package com.olaenmanijo.weatherbasedtravelplanner.tourapi.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.olaenmanijo.weatherbasedtravelplanner.tourapi.domain.PlaceResponse;

@Mapper
public interface PlaceMapper {

    List<PlaceResponse> selectPlaceByAreaAndCategory(Map<String, String> paramMap);
}
