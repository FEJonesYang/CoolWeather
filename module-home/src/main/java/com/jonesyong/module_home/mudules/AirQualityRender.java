package com.jonesyong.module_home.mudules;

import com.jonesyong.library_common.model.Response;
import com.jonesyong.module_home.WeatherActivity;

/**
 * @Author JonesYang
 * @Date 2022-01-10
 * @Description
 */
public class AirQualityRender {

    private WeatherActivity mWeatherActivity;


    public AirQualityRender(WeatherActivity weatherActivity) {
        this.mWeatherActivity = weatherActivity;
    }

    /**
     * 空气质量的处理，这里只有两个数据
     *
     * @param response 空气质量
     */
    public void handleAirConditionData(Response response) {
        if (response == null || response.getNow() == null) {
            return;
        }
        mWeatherActivity.mTvAqi.setText(response.getNow().getAqi());
        mWeatherActivity.mTvPm25.setText(response.getNow().getPm2p5());
    }

}
