package com.olaenmanijo.weatherbasedtravelplanner.exception;

public class PlansNotFoundException extends RuntimeException {

    public PlansNotFoundException() {
        super();
    }

    public PlansNotFoundException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PlansNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlansNotFoundException(String message) {
        super(message);
    }

    public PlansNotFoundException(Throwable cause) {
        super(cause);
    }
}
