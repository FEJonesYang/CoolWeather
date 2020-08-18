package com.example.kuou.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @author JonesYang
 * @Data 2020-08-18
 * @Function
 */
public class Basic {

    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
