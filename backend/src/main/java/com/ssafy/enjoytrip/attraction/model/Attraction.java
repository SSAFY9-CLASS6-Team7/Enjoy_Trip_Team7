package com.ssafy.enjoytrip.attraction.model;

import lombok.Data;

@Data
public class Attraction {
    private int attractionId;
    private String title;
    private int code;
    private double lat;
    private double lng;
    private String address;
    private String overView;
    private int sido;
    private String thumbnail;
}
