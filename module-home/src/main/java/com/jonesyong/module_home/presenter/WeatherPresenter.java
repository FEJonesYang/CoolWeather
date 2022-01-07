package com.jonesyong.module_home.presenter;

import android.util.Log;

import com.jonesyong.library_common.common.json.Utility;
import com.jonesyong.library_common.common.net.Api;
import com.jonesyong.library_common.common.net.HttpUtil;
import com.jonesyong.library_common.model.AirNowConditionResponse;
import com.jonesyong.library_common.model.BackgroundImageData;
import com.jonesyong.library_common.model.DailyResponse;
import com.jonesyong.library_common.model.LifestyleResponse;
import com.jonesyong.library_common.model.NowResponse;
import com.jonesyong.module_home.interfaces.WeatherDataCallback;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author JonesYang
 * @Data 2021-05-29
 * @Function 天气数据的实际提供者
 */
public class WeatherPresenter {

    private static final String TAG = "WeatherPresenter";

    // 单例模式
    private static volatile WeatherPresenter instance;

    private WeatherPresenter() {
    }

    public static WeatherPresenter getInstance() {
        if (instance == null) {
            synchronized (WeatherPresenter.class) {
                if (instance == null) {
                    instance = new WeatherPresenter();
                }
            }
        }
        return instance;
    }

    // 持有数据回调的成员变量
    private WeatherDataCallback mWeatherDataCallback;

    /**
     * 发起请求当前天气数据的
     */
    public void requestNowWeatherData(String locationId) {
        HttpUtil.sendOkHttpRequest(Api.weatherNow + locationId, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "获取当前的天气信息失败---> " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                NowResponse nowResponse = Utility.getGsonInstance().fromJson(response.body().string(), NowResponse.class);
                Log.d(TAG, nowResponse.toString());
                mWeatherDataCallback.nowWeatherDataCallback(nowResponse);
            }
        });
    }


    /**
     * 发起请求空气质量
     */
    public void requestAirConditionData(String locationId) {

        HttpUtil.sendOkHttpRequest(Api.airCondition + locationId, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                AirNowConditionResponse airNowConditionResponse = Utility.getGsonInstance().fromJson(response.body().string(), AirNowConditionResponse.class);
                mWeatherDataCallback.airConditionDataCallback(airNowConditionResponse);
            }
        });
    }

    /**
     * 发起请求天气预报的请求--3Day
     */
    public void requestForecast3DayData(String locationId) {

        HttpUtil.sendOkHttpRequest(Api.forecast3Day + locationId, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                DailyResponse dailyResponse = Utility.getGsonInstance().fromJson(response.body().string(), DailyResponse.class);
                mWeatherDataCallback.weatherForecastDataCallback(dailyResponse);
            }
        });
    }

    /**
     * 发起请求天气预报的请求--7Day
     */
    public void requestForecast7DayData(String locationId) {

        HttpUtil.sendOkHttpRequest(Api.forecast7Day + locationId, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                DailyResponse dailyResponse = Utility.getGsonInstance().fromJson(response.body().string(), DailyResponse.class);
                mWeatherDataCallback.weatherForecastDataCallback(dailyResponse);
            }
        });
    }

    /**
     * 发起请求生活指数
     */
    public void requestLifeConditionData(String locationId) {
        HttpUtil.sendOkHttpRequest(Api.lifeCondition + locationId, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                LifestyleResponse lifestyleResponse = Utility.getGsonInstance().fromJson(response.body().string(), LifestyleResponse.class);
                mWeatherDataCallback.lifeConditionDataCallback(lifestyleResponse);
            }
        });
    }

    /**
     * 发起天气预警信息的查询
     */
    public void requestWarmNowData(String locationId) {
        HttpUtil.sendOkHttpRequest(Api.weatherWarn + locationId, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }

    /**
     * 发起天气预警信息城市的查询
     */
    public void requestWarmNowCityListData() {
        HttpUtil.sendOkHttpRequest(Api.warnCityList, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }

    /**
     * 发起历史天气数据的查询
     */
    public void requestHistoryWeatherData() {
        HttpUtil.sendOkHttpRequest(Api.historyWeather, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }

    /**
     * 发起历史空气质量数据的查询
     */
    public void requestHistoryAirConditionData() {
        HttpUtil.sendOkHttpRequest(Api.historyAirCondition, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }

    /**
     * 请求背景图片的
     */
    public void requestBackgroundImage() {
        HttpUtil.sendOkHttpRequest(Api.getBackgroundImageUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "网络图片获取出现错误---> " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                BackgroundImageData backgroundImageData = Utility.getGsonInstance()
                        .fromJson(response.body().string(), BackgroundImageData.class);
                Log.d(TAG, "https://cn.bing.com" +
                        backgroundImageData.getImages().get(0).getUrl());
                // 请求背景图片的时候，需要域名加上请求返回的 url
                mWeatherDataCallback.loadImageUrlDataCallback("https://cn.bing.com" +
                        backgroundImageData.getImages().get(0).getUrl());
            }
        });
    }

    public void setWeatherDataCallback(WeatherDataCallback weatherDataCallback) {
        mWeatherDataCallback = weatherDataCallback;
    }
}






