package com.olaenmanijo.weatherbasedtravelplanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;

@Controller
public class ErrorPageController {

    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        return "/error-page/404";
    }
}
