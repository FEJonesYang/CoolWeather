package com.example.kuou.gson;

/**
 * @author JonesYang
 * @Data 2020-08-18
 * @Function
 */
public class AQI {

    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
