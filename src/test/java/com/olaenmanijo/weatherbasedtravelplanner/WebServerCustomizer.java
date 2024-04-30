package com.olaenmanijo.weatherbasedtravelplanner;

import org.springframework.http.HttpStatus;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
        factory.addErrorPages(errorPage404);
    }
}
