package com.example.kuou.module.weather.interfaces;

import com.example.kuou.gson.Now;
import com.example.kuou.module.weather.model.AirNowResponse;
import com.example.kuou.module.weather.model.DailyResponse;
import com.example.kuou.module.weather.model.LifestyleResponse;
import com.example.kuou.module.weather.model.NowResponse;
import com.example.kuou.module.weather.model.WarmNowCityListResponse;
import com.example.kuou.module.weather.model.WarmNowResponse;

/**
 * @author JonesYang
 * @Data 2021-06-01
 * @Function 该接口用于在WeatherPresenter获取数据之后，
 * 把数据回调给 Activity ，但是需要注意线程切换，不能在子线程只能够更新UI
 */
public interface WeatherDataCallback {


    /**
     * 当前的天气数据
     *
     * @param nowResponse
     */
    void NowWeatherDataCallback(NowResponse nowResponse);

    /**
     * 生活指数的接口
     *
     * @param lifestyleResponse
     */
    void LifeConditionDataCallback(LifestyleResponse lifestyleResponse);

    /**
     * 空气质量的数据接口
     *
     * @param airNowResponse
     */
    void AirConditionDataCallback(AirNowResponse airNowResponse);

    /**
     * 天气预报
     *
     * @param dailyResponse
     */
    void AirConditionDataCallback(DailyResponse dailyResponse);

    /**
     * 天气预警信息
     *
     * @param warmNowResponse
     */
    void WarmNowDataCallback(WarmNowResponse warmNowResponse);

    /**
     * 天气预警城市列表的查询
     *
     * @param warmNowCityListResponse
     */
    void WarmNowCityListDataCallback(WarmNowCityListResponse warmNowCityListResponse);


}
