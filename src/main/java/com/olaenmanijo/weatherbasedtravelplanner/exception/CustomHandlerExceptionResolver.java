package com.olaenmanijo.weatherbasedtravelplanner.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) {
        
        try {
            log.error("{}", ex.getMessage(), ex);
            
            DefaultResponse defaultResponse = new DefaultResponse(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.SERVER_ERROR);
            String result = objectMapper.writeValueAsString(defaultResponse);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(result);
            return new ModelAndView();
        } catch (Exception e) {
            log.error("{}", ex.getMessage(), ex);
        }
        
        return null;
    }
}
