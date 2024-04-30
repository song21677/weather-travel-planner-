package com.olaenmanijo.weatherbasedtravelplanner.plan_after;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import com.amazonaws.Response;
import com.olaenmanijo.weatherbasedtravelplanner.domain.member.dto.response.MemberResponse;
import com.olaenmanijo.weatherbasedtravelplanner.exception.DefaultResponse;
import com.olaenmanijo.weatherbasedtravelplanner.exception.ErrorResponse;
import com.olaenmanijo.weatherbasedtravelplanner.exception.ResponseMessage;
import com.olaenmanijo.weatherbasedtravelplanner.exception.SessionExpiredException;
import com.olaenmanijo.weatherbasedtravelplanner.exception.StatusCode;
import com.olaenmanijo.weatherbasedtravelplanner.exception.PlansNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PlanController2 {
    private final PlanMapper planMapper;
    
    @ResponseBody
    @ExceptionHandler
    public DefaultResponse<List<ErrorResponse>> handleMethodArgumentNotValidEx(MethodArgumentNotValidException e, BindingResult bindingResult) {
        List<ErrorResponse> errorResponses = new ArrayList<ErrorResponse>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            log.warn("{}", error.getField());
            log.warn("{}", error.getRejectedValue());
            String rejectedValue = (error.getRejectedValue() != null) ? error.getRejectedValue().toString() : "null";
            errorResponses.add(new ErrorResponse("body", error.getField(), rejectedValue, "syntax error", error.getDefaultMessage()));
        }
        return new DefaultResponse<List<ErrorResponse>>(StatusCode.BAD_MESSAGE, ResponseMessage.BAD_MESSAGE, errorResponses);
    }
    
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler
    public DefaultResponse handlePlansNotFoundEx(PlansNotFoundException e) {
        return new DefaultResponse(StatusCode.DATA_NOT_FOUND, e.getMessage());
    }
    
    @ExceptionHandler
    public DefaultResponse<ErrorResponse> handleHttpMessageNotReadableEx(HttpMessageNotReadableException e) {
        try {
            String messageBody = StreamUtils.copyToString(e.getHttpInputMessage().getBody(), StandardCharsets.UTF_8);
            return new DefaultResponse<ErrorResponse>(StatusCode.BAD_MESSAGE, ResponseMessage.BAD_MESSAGE, new ErrorResponse("body", messageBody, "syntaxError", "유효하지 않은 JSON 형식"));
        } catch (IOException e1) {
            // 어케 처리할 것인지
            e1.printStackTrace();
            return new DefaultResponse<ErrorResponse>(StatusCode.BAD_MESSAGE, ResponseMessage.BAD_MESSAGE);
        }
    }
    
    @ResponseBody
    @ExceptionHandler
    public DefaultResponse handleHttpMessageConvesionEx(HttpMessageConversionException e) {
        return new DefaultResponse(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.SERVER_ERROR);
    }
    
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(SessionExpiredException.class)    // ExceptionHandlerResolver? 갔다가 어노테이션 있는지 확인하구 예외 잡음.. try catch같이?? 정상처리 됨
    public DefaultResponse sesseionExHandler(SessionExpiredException e) {    
        return new DefaultResponse(StatusCode.UNAUTHORIZED, e.getMessage());
    }
    
    @ResponseBody
    @PostMapping("/plans")
    @Transactional  // 클래스 단위?? 메소드 단위?? // 예외 처리 필요, transactional
    public DefaultResponse savePlan(HttpSession session, @Valid @RequestBody Plan2DTO planDTO) {
        MemberResponse memberResponse = (MemberResponse) session.getAttribute("loginMember");
        if (memberResponse == null) {
            log.warn("{}", "세션 만료!");
            throw new SessionExpiredException(ResponseMessage.SESSION_EXPIRED);
        }
        
        Plan2 plan = new Plan2(memberResponse.getMemberNo(), planDTO.getTitle(), planDTO.getStartDate(), planDTO.getEndDate());        
        planMapper.insertPlan(plan);
        
        ArrayList<DetailPlanDTO> detailPlans = (ArrayList<DetailPlanDTO>) planDTO.getDetailPlans();
        // 여기서 long으로 바꾸고.. 실제 날짜도 여기서 바꾸고.. 프론트에서 애초에 long과 실제 날짜로 보내주면 되지 않나?? 어디서 바꿔야 하는곤지 흠흠
        for (DetailPlanDTO detailPlanDTO : detailPlans) {
            String realDate = changeRealDate(detailPlanDTO.getDate(), planDTO.getStartDate());
            DetailPlan detailPlan = new DetailPlan(plan.getId(), Long.parseLong(detailPlanDTO.getPlaceId()), realDate, detailPlanDTO.getStartHour(), detailPlanDTO.getEndHour());
            planMapper.insertDetailPlan(detailPlan);
        }
  
        return new DefaultResponse(StatusCode.OK);
    }
    
    public String changeRealDate(String date, String startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate startLocalDate = LocalDate.parse(startDate, formatter);
        LocalDate realDate = startLocalDate.plusDays(Integer.parseUnsignedInt(date));
        
        return realDate.format(formatter);
    }
    
    @ResponseBody
    @GetMapping("/plans")
    @Transactional
    public DefaultResponse<List<PlanResponse>> showPlans(HttpSession session) {
        MemberResponse memberResponse = (MemberResponse)session.getAttribute("loginMember");
        // 자기 자신만 마이페이지에서 볼 수 있음.
        if (memberResponse == null) {
            log.warn("{}", "세션 만료!");
            throw new SessionExpiredException(ResponseMessage.SESSION_EXPIRED);
        }
        
        List<PlanResponse> plans = (ArrayList<PlanResponse>) planMapper.selectPlanByMemberNo(memberResponse.getMemberNo());
        // 조회 결과 없을 때
        if (plans.size() == 0) {
            log.warn("{}", "여행 일정 목록 조회 없음");
            throw new PlansNotFoundException(ResponseMessage.DATA_NOT_FOUND);
        }
        
        // 회원 탈퇴했을 때.. 이건 DB 설정해줘야 하나..?
        
        for (PlanResponse plan : plans) {
            List<Links> links = new ArrayList<>();
            links.add(new Links("detailPlan", "plans/" + plan.getId() + "/detailPlans", "GET", new String[] {"appliction/json"}));
            links.add(new Links("self", "plans/" + plan.getId(), "GET", new String[] {"appliction/json"}));
            links.add(new Links("self", "plans/" + plan.getId(), "DELETE"));
            
            plan.setLinks(links);
        }
        
        // 조회 결과 전달
        return new DefaultResponse<List<PlanResponse>>(StatusCode.OK, ResponseMessage.SUCCESS, plans);
    }
    
    // 1. detailPlan과 같이 줘야 할지
    // 2. 아니면 클라이언트에서 다시 /plans/{planId}/detailPlans API를 요청해야 할지.. v
    @ResponseBody
    @GetMapping("/plans/{planId}")
    public DefaultResponse<PlanResponse> showPlan(@PathVariable long planId, HttpSession session) {
        MemberResponse memberResponse = (MemberResponse)session.getAttribute("loginMember");
        // 자기 자신만 마이페이지에서 볼 수 있음.
        if (memberResponse == null) {
            log.warn("{}", "세션 만료!");
            throw new SessionExpiredException(ResponseMessage.SESSION_EXPIRED);
        }
        
        PlanResponse plan = planMapper.selectPlanByTravelPlanNo(planId);
        log.debug("{}", plan == null);
        if (plan == null) {
            log.warn("{}", "선택한 여행 일정 조회 없음");
            throw new PlansNotFoundException(ResponseMessage.DATA_NOT_FOUND);
        }
        
        List<Links> links = new ArrayList<>();
        links.add(new Links("self", "plans/" + planId, "PUT", new String[] {"application/json"}));
        plan.setLinks(links);
        
        return new DefaultResponse<PlanResponse>(StatusCode.OK, ResponseMessage.SUCCESS, plan);
    }
    
    @ResponseBody
    @GetMapping("/plans/{planId}/detailPlans")
    public DefaultResponse<List<DetailPlanResponse>> showDetailPlan(@PathVariable long planId, HttpSession session) {     
        MemberResponse memberResponse = (MemberResponse)session.getAttribute("loginMember");
        // 자기 자신만 마이페이지에서 볼 수 있음.
        if (memberResponse == null) {
            log.warn("{}", "세션 만료!");
            throw new SessionExpiredException(ResponseMessage.SESSION_EXPIRED);
        }
        
        List<DetailPlanResponse> detailPlans = planMapper.selectDetailPlanByTravelPlanNo(planId);
        log.debug("{}", detailPlans);
        // 조회 결과 없을 때 해줘야 하나..? 왜냐 세부 일정을 추가 안해도 됐기 때문에...
        // 근데 조회 결과가 없을 때 없는 planId를 조회한건지 아님 세부 일정을 추가 안해서인지 구분이 안가네 x
//        if (detailPlans.size() == 0) {
//            throw new PlansNotFoundException(ResponseMessage.DATA_NOT_FOUND);
//        }
        
        for (DetailPlanResponse detailPlan : detailPlans) {
            List<Links> links = new ArrayList<>();
            links.add(new Links("self", "plans/" + detailPlan.getId(), "PUT", new String[] {"application/json"}));
            links.add(new Links("self", "plans/" + detailPlan.getId(), "DELETE"));
            
            detailPlan.setLinks(links);
        }
        
        return new DefaultResponse<List<DetailPlanResponse>>(StatusCode.OK, ResponseMessage.SUCCESS, detailPlans);
    }
    
    @PutMapping("/plans/{planId}") 
    public void editPlan(@PathVariable long planId) {
        
    }
    
    @DeleteMapping("/plans/{planId}")
    public void deletePlan() {
        
    }
    
}
