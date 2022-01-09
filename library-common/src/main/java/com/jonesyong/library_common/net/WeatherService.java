package com.jonesyong.library_common.net;

import com.jonesyong.library_common.base.Constants;
import com.jonesyong.library_common.model.LifestyleResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @Author JonesYang
 * @Date 2022-01-08
 * @Description
 */
public interface WeatherService {

    String geoBaseUrl = "https://geoapi.qweather.com";

    String devBaseUrl = "https://devapi.qweather.com";


    @GET("v7/indices/1d?type=0" + Constants.HWeatherKey)
    Observable<LifestyleResponse> getSuggestResult(@Query("location") String locationId); // 生活指数

}
