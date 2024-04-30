package com.olaenmanijo.weatherbasedtravelplanner.plan_after;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Links {

    private String rel;
    private String href;
    private String action;
    private String[] types;
    
    public Links(String rel, String href, String action) {
        this.rel = rel;
        this.href = href;
        this.action = action;
    }
}
