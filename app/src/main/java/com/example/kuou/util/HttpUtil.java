package com.example.kuou.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author JonesYang
 * @Data 2020-08-16
 * @Function okHttp 封装的网络请求
 */
public class HttpUtil {

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
