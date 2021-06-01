package com.example.kuou.module.weather.presenter;

import com.example.kuou.common.net.Api;
import com.example.kuou.common.net.HttpUtil;
import com.example.kuou.module.weather.interfaces.WeatherDataCallback;

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

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

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

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

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

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

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

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

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

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

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

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }


}






