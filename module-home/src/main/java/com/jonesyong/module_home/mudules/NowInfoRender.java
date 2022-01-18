package com.jonesyong.module_home.mudules;

import android.annotation.SuppressLint;

import com.jonesyong.library_common.model.Response;
import com.jonesyong.module_home.WeatherActivity;

import org.jetbrains.annotations.NotNull;

/**
 * @Author JonesYang
 * @Date 2022-01-10
 * @Description 处理当前的天气数据
 */
public class NowInfoRender {

    private WeatherActivity mWeatherActivity;

    public NowInfoRender(WeatherActivity weatherActivity) {
        this.mWeatherActivity = weatherActivity;
    }

    /**
     * 更新当前的天气数据
     *
     * @param nowResponse base response
     */
    @SuppressLint("SetTextI18n")
    public void handleNowWeatherData(@NotNull Response nowResponse) {
        if (nowResponse.getNow() == null || nowResponse.getUpdateTime().isEmpty()) {
            return;
        }
        // 温度
        mWeatherActivity.degreeText.setText(nowResponse.getNow().getTemp() + "℃");
        // 天气状况
        mWeatherActivity.weatherInfoText.setText(nowResponse.getNow().getText());
        // 风向
        mWeatherActivity.mWinDirection.setText(nowResponse.getNow().getWindDir());
        // 湿度
        mWeatherActivity.mHumidity.setText(nowResponse.getNow().getHumidity());
        // 气压
        mWeatherActivity.mAirPressure.setText(nowResponse.getNow().getPressure());
        // 更新时间
        mWeatherActivity.mUpdateTime.setText("更新时间： " + nowResponse.getUpdateTime().substring(11, 16));
    }
}
