package com.olaenmanijo.weatherbasedtravelplanner.tourapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.olaenmanijo.weatherbasedtravelplanner.exception.DefaultResponse;
import com.olaenmanijo.weatherbasedtravelplanner.exception.PlacesNotFoundException;
import com.olaenmanijo.weatherbasedtravelplanner.exception.ResponseMessage;
import com.olaenmanijo.weatherbasedtravelplanner.exception.StatusCode;
import com.olaenmanijo.weatherbasedtravelplanner.plan_after.Links;
import com.olaenmanijo.weatherbasedtravelplanner.tourapi.dao.PlaceMapper;
import com.olaenmanijo.weatherbasedtravelplanner.tourapi.domain.PlaceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceMapper placeMapper;

//    @ResponseBody
//    @GetMapping("/places/{placeId}")
//    public DefaultResponse<> showPlace(@PathVariable long placeId) {
//        
//        
//        return new DefaultResponse<>();
//    }
    
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler
    public DefaultResponse handlePlacesNotFoundException(PlacesNotFoundException e) {
        return new DefaultResponse(StatusCode.DATA_NOT_FOUND, e.getMessage());
    }
    
    @ResponseBody
    @GetMapping("/places")
    public DefaultResponse searchPlace(@RequestParam(required = false) String area, 
            @RequestParam(required = false) String category, @RequestParam(required = false) String keyword) {
        
        if (allEmpty(area, category, keyword) || oneEmpty(area, category)) {
            // 개발 미완
            return new DefaultResponse(StatusCode.BAD_MESSAGE);
        }
        
        if (keyword != null) {
            
        } else {
            return searchByAreaAndCategory(area, category);
        }
        
        return new DefaultResponse(StatusCode.BAD_MESSAGE);
    }
    
    private DefaultResponse<List<PlaceResponse>> searchByAreaAndCategory(String area, String category) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("area", area);
        paramMap.put("category", category);
        
        List<PlaceResponse> places = placeMapper.selectPlaceByAreaAndCategory(paramMap);
        
        // 조회 결과 없을 때
        if (places.size() == 0) {
            log.warn("{}", "장소 조회 없음");
            throw new PlacesNotFoundException(ResponseMessage.DATA_NOT_FOUND);
        }
        
        for (PlaceResponse place : places) {
            List<Links> links = new ArrayList<>();
            //links.add(new Links(""))
            place.setLinks(links);
        }
        
        return new DefaultResponse<List<PlaceResponse>>(StatusCode.OK, ResponseMessage.SUCCESS, places);
    }

    private boolean allEmpty(String area, String category, String keyword) {
        if (area == null && category == null && keyword == null) return true;
        return false;
    }
    
    private boolean oneEmpty(String area, String category) {
        if (area != null && category == null || area == null && category != null) return true;
        return false;
    }
    
}

/*
 * /plans - 마이페이지 일정 목록, 리뷰 작성 시
 * /plans/id - 리뷰 작성 시, 마이페이지 일정 보기
 * /plans/id/detailPlans - 리뷰 작성시, 마이페이지 일정 보기
 * /places 
 * /places/id
 * /places?area=${area}&category=${category}
 * 
*/