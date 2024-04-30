package com.olaenmanijo.weatherbasedtravelplanner.plan_after;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanMapper {

    long insertPlan(Plan2 plan);

    long insertDetailPlan(DetailPlan detailPlan);

    List<PlanResponse> selectPlanByMemberNo(long memberNo);

    PlanResponse selectPlanByTravelPlanNo(long travelPlanNo);

    List<DetailPlanResponse> selectDetailPlanByTravelPlanNo(long travelPlanNo);
}
