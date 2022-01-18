package com.jonesyong.module_home;


import com.jonesyong.library_common.model.Response;
import com.jonesyong.library_common.net.HttpUtil;
import com.jonesyong.module_home.mudules.AirQualityRender;
import com.jonesyong.module_home.mudules.ForecastInfoRender;
import com.jonesyong.module_home.mudules.NowInfoRender;
import com.jonesyong.module_home.mudules.SuggestInfoRender;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author JonesYang
 * @Data 2022-01-11
 * @Function 天气数据的实际提供者
 */
public class WeatherPresenter {

    private static final String TAG = "WeatherPresenter";

    private WeatherActivity mWeatherActivity;

    // 首页模块较多，因此在请求完成之后交给对应的 render 去渲染布局
    private final NowInfoRender mNowInfoRender;
    private final ForecastInfoRender mForecastInfoRender;
    private final SuggestInfoRender mSuggestInfoRender;
    private final AirQualityRender mAirQualityRender;

    public WeatherPresenter(WeatherActivity weatherActivity) {
        this.mWeatherActivity = weatherActivity;
        mNowInfoRender = new NowInfoRender(mWeatherActivity);
        mForecastInfoRender = new ForecastInfoRender(mWeatherActivity);
        mSuggestInfoRender = new SuggestInfoRender(mWeatherActivity);
        mAirQualityRender = new AirQualityRender(mWeatherActivity);
    }

    /**
     * 查询当前的天气数据
     *
     * @param location location
     */
    public void getNowWeatherInfo(String location) {
        Observable<Response> observable = HttpUtil.getDevService().getNowDailyInfo(location);
        observable.observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mNowInfoRender::handleNowWeatherData);
    }

    /**
     * 查询当前的天气预报
     *
     * @param location location
     */
    public void getForecastInfo(String location) {
        Observable<Response> observable = HttpUtil.getDevService().getForecastInfo(location);
        observable.observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mForecastInfoRender::handleForecastWeatherData);
    }

    /**
     * 获取空气质量
     *
     * @param location location
     */
    public void getAirQualityInfo(String location) {
        Observable<Response> observable = HttpUtil.getDevService().getAirQualityInfo(location);
        observable.observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mAirQualityRender::handleAirConditionData);
    }

    /**
     * 获取空气生活建议
     *
     * @param location location
     */
    public void getSuggestionInfo(String location) {
        Observable<Response> observable = HttpUtil.getDevService().getSuggestResult(location);
        observable.observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSuggestInfoRender::handleLifeSuggestion);
    }

    /**
     * 背景图片信息
     */
    public void getBackgroundImage() {
        Observable<Response> observable = HttpUtil.buildService("https://cn.bing.com/").getBackgroundImage();
        observable.observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    mWeatherActivity.loadImageUrlDataCallback("https://cn.bing.com" + response.getImages().get(0).getUrl());
                });
    }

}






