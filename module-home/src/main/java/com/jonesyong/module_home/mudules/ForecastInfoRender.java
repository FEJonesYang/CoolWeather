package com.jonesyong.module_home.mudules;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jonesyong.library_common.model.DailyModel;
import com.jonesyong.library_common.model.Response;
import com.jonesyong.module_home.R;
import com.jonesyong.module_home.WeatherActivity;

import java.util.List;

/**
 * @Author JonesYang
 * @Date 2022-01-10
 * @Description
 */
public class ForecastInfoRender {

    private WeatherActivity mWeatherActivity;

    public ForecastInfoRender(WeatherActivity weatherActivity) {
        this.mWeatherActivity = weatherActivity;
    }

    /**
     * 天气预报UI展示处理
     *
     * @param dailyResponse
     */
    @SuppressLint("SetTextI18n")
    public void handleForecastWeatherData(Response dailyResponse) {
        if (dailyResponse == null || dailyResponse.getDaily() == null) {
            return;
        }
        // 解决数据重复添加的问题
        List<DailyModel> dailyForecastList = dailyResponse.getDaily();
        mWeatherActivity.mForecastWeatherContainer.removeAllViews();

        for (int i = 0; i < dailyForecastList.size(); i++) {
            View view = LayoutInflater.from(mWeatherActivity).inflate(R.layout.item_forecast, null, false);
            TextView forecastDate = view.findViewById(R.id.tv_forecast_date);
            TextView forecastDay = view.findViewById(R.id.tv_forecast_day);
            TextView forecastNight = view.findViewById(R.id.tv_forecast_night);
            TextView forecastWinDirection = view.findViewById(R.id.tv_forecast_win_direction);
            TextView forecastTemperature = view.findViewById(R.id.tv_forecast_temperature);
            // 设置数据
            forecastDate.setText(dailyResponse.getDaily().get(i).getFxDate());
            forecastDay.setText(dailyResponse.getDaily().get(i).getTextDay());
            forecastNight.setText(dailyResponse.getDaily().get(i).getTextNight());
            forecastWinDirection.setText(dailyResponse.getDaily().get(i).getWindDirDay());
            forecastTemperature.setText(dailyResponse.getDaily().get(i).getTempMax() + "℃" + "~" + dailyResponse.getDaily().get(i).getTempMin() + "℃");
            mWeatherActivity.mForecastWeatherContainer.addView(view);
        }
    }
}
