package com.jonesyong.module_map;

import android.annotation.SuppressLint;
import android.util.Log;

import com.jonesyong.library_common.json.Utility;
import com.jonesyong.library_common.net.Api;
import com.jonesyong.library_common.net.HttpUtil;
import com.jonesyong.library_common.model.NowResponse;
import com.jonesyong.library_common.model.SearchCityBean;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @Author JonesYang
 * @Date 2021-06-12
 * @Description 地图界面提供天气数据的
 */
public class MapWeatherPresenter {
    private static final String TAG = "MapWeatherPresenter";

    public MapWeatherPresenter() {
    }

    /**
     * 发起请求当前天气数据的
     */
    public void requestNowWeatherData(String locationId) {
        HttpUtil.sendOkHttpRequest(Api.weatherNow + locationId, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "获取当前的天气信息失败---> " + e.getMessage());
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                NowResponse nowResponse = Utility.getGsonInstance().fromJson(response.body().string(), NowResponse.class);
                mapWeatherListener.postMapWeatherData(nowResponse);
            }
        });
    }

    // 查询城市,在这里需要根据经度纬度信息得到城市的信息
    public void queryLocationCity(String location) {
        HttpUtil.sendOkHttpRequest(Api.queryCity + location, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, e.getMessage());
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                SearchCityBean searchCityBean = Utility.getGsonInstance().fromJson(response.body().string(), SearchCityBean.class);
                mapWeatherListener.postCityName(searchCityBean.getLocation().get(0).getName());
            }
        });
    }

    public void setMapWeatherListener(ISendMapWeatherListener mapWeatherListener) {
        this.mapWeatherListener = mapWeatherListener;
    }

    public ISendMapWeatherListener mapWeatherListener;

    public interface ISendMapWeatherListener {
        void postMapWeatherData(NowResponse nowResponse);

        void postCityName(String name);
    }
}
