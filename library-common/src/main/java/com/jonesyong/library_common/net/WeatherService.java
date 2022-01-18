package com.jonesyong.library_common.net;

import com.jonesyong.library_common.base.Constants;
import com.jonesyong.library_common.model.Response;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @Author JonesYang
 * @Date 2022-01-08
 * @Description api service
 */
public interface WeatherService {

    @GET("v7/indices/1d?type=0" + Constants.HWeatherKey)
    Observable<Response> getSuggestResult(@Query("location") String locationId); // 生活指数

    @GET("v7/weather/now?" + Constants.HWeatherKey)
    Observable<Response> getNowDailyInfo(@Query("location") String location); // 实时天气数据

    @GET("v2/city/lookup?" + Constants.HWeatherKey)
    Observable<Response> getCityInfo(@Query("location") String location); // 城市查询

    @GET("v2/city/top?number=20&range=cn" + Constants.HWeatherKey)
    Observable<Response> getHotCityInfo(); // 热门城市，用于搜索,展示中国的top20

    @GET("v7/weather/3d?" + Constants.HWeatherKey)
    Observable<Response> getForecastInfo(@Query("location") String location); // 天气预报

    @GET("v7/air/now?" + Constants.HWeatherKey)
    Observable<Response> getAirQualityInfo(@Query("location") String location); // 空气质量

    @GET("HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN")
    Observable<Response> getBackgroundImage(); // 背景图片信息

    @GET("v7/warning/list?range=cn" + Constants.HWeatherKey)
    Observable<Response> getWarnCityList();

    @GET("v7/warning/now?" + Constants.HWeatherKey)
    Observable<Response> getWarnCityInfo(@Query("location") String location);

}
