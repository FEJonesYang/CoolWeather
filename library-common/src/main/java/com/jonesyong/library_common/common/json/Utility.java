package com.jonesyong.library_common.common.json;


import com.google.gson.Gson;


/**
 * @author JonesYang
 * @Data 2020-08-16
 * @Function 返回Gson 对象
 */


public class Utility {
    // 返回一个 Gson 对象
    public static Gson getGsonInstance() {
        Gson gson = new Gson();
        return gson;
    }
}

