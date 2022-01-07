package com.jonesyong.module_home.interfaces;


import com.jonesyong.library_common.model.AirNowConditionResponse;
import com.jonesyong.library_common.model.DailyResponse;
import com.jonesyong.library_common.model.LifestyleResponse;
import com.jonesyong.library_common.model.NowResponse;
import com.jonesyong.library_common.model.WarmNowCityListResponse;
import com.jonesyong.library_common.model.WarmNowResponse;

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
    void nowWeatherDataCallback(NowResponse nowResponse);

    /**
     * 生活指数的接口
     *
     * @param lifestyleResponse
     */
    void lifeConditionDataCallback(LifestyleResponse lifestyleResponse);

    /**
     * 空气质量的数据接口
     *
     * @param airNowConditionResponse
     */
    void airConditionDataCallback(AirNowConditionResponse airNowConditionResponse);

    /**
     * 天气预报,分3天和7天
     *
     * @param dailyResponse
     */
    void weatherForecastDataCallback(DailyResponse dailyResponse);

    /**
     * 天气预警信息
     *
     * @param warmNowResponse
     */
    void warmNowDataCallback(WarmNowResponse warmNowResponse);

    /**
     * 天气预警城市列表的查询
     *
     * @param warmNowCityListResponse
     */
    void warmNowCityListDataCallback(WarmNowCityListResponse warmNowCityListResponse);

    /**
     * 图片加载的
     *
     * @param url
     */
    void loadImageUrlDataCallback(String url);


}
