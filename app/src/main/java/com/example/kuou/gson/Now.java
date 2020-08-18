package com.example.kuou.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @author JonesYang
 * @Data 2020-08-18
 * @Function
 */
public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More{
        @SerializedName("txt")
        public String info;
    }
}
