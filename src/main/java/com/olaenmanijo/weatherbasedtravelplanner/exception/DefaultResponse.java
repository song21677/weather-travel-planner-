package com.olaenmanijo.weatherbasedtravelplanner.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DefaultResponse<T> {

    private int statusCode;
    private String responseMessage;
    private T data;
    
    public DefaultResponse(int statusCode) {
        super();
        this.statusCode = statusCode;
    }
    
    public DefaultResponse(int statusCode, String responseMessage) {
        super();
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = null;
    }
    
}
