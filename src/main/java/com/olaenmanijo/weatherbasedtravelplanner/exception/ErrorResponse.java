package com.olaenmanijo.weatherbasedtravelplanner.exception;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private String location;
    private String param;
    private String value;
    private String error;
    private String msg;

     public ErrorResponse(String location, String value, String error, String msg) {
     
         this.location = location;
         this.value = value;
         this.error = error;
         this.msg = msg;
     }
    
}
