package com.olaenmanijo.weatherbasedtravelplanner.exception;

public class SessionExpiredException extends RuntimeException {

    public SessionExpiredException() {
        super();
    }

    public SessionExpiredException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

    public SessionExpiredException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public SessionExpiredException(String arg0) {
        super(arg0);
    }

    public SessionExpiredException(Throwable arg0) {
        super(arg0);
    }
}
