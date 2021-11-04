package com.example.kuou.common.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.kuou.base.Constants;

import java.util.logging.Handler;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author JonesYang
 * @Data 2020-08-16
 * @Function okHttp 封装的网络请求
 */
public class HttpUtil {

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {

        //1、创建一个 OkHttpClient 对象
        OkHttpClient client = new OkHttpClient();
        //2、创建一个 Request 请求
        Request request = new Request.Builder().url(address + Constants.HWeatherKey).build();
        //3、使用 第一步和第二部 创建的对象发送网络请求
        client.newCall(request).enqueue(callback);
    }

    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }
}
