package com.ravindra.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AviationData {
    private String siteNumber;
    private String type;
    private String state;
    private String city;
    private String county;
    private String latitude;
    private String longitude;
    private String militaryLanding;
}
