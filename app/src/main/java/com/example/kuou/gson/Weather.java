package com.example.kuou.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author JonesYang
 * @Data 2020-08-18
 * @Function  GSON 实体类
 */
public class Weather {

    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
