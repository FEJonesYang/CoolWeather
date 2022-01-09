package com.jonesyong.library_common.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.jonesyong.library_common.base.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author JonesYang
 * @Data 2020-08-16
 * @Function okHttp 封装的网络请求
 */
public class HttpUtil {

    @Deprecated
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


    /**
     * 和风天气的 api 提供了两种 host，分别用来查询天气和地理信息
     *
     * @return WeatherService
     */
    public static WeatherService getGeoService() {
        return buildService("https://geoapi.qweather.com/");
    }

    public static WeatherService getDevService() {
        return buildService("https://devapi.qweather.com/");
    }

    /**
     * @param host host
     * @return 返回接口服务
     */
    public static WeatherService buildService(@NotNull String host) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(buildHttpClient())
                .build();
        return retrofit.create(WeatherService.class);
    }

    public static OkHttpClient buildHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS);
        return builder.build();
    }
}
