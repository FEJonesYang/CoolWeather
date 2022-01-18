package com.jonesyong.module_map;


import android.util.Log;

import com.jonesyong.library_common.model.Response;
import com.jonesyong.library_common.net.HttpUtil;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @Author JonesYang
 * @Date 2021-06-12
 * @Description 地图界面提供天气数据的
 */
public class MapWeatherPresenter {
    private static final String TAG = "MapWeatherPresenter";
    MapActivity mMapActivity;

    public MapWeatherPresenter(MapActivity mapActivity) {
        this.mMapActivity = mapActivity;
    }

    /**
     * 获取当前位置的天气数据
     *
     * @param location location
     */
    public void getNowWeatherInfo(String location) {
        Observable<Response> observable = HttpUtil.getDevService().getNowDailyInfo(location);
        observable.observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response response) {
                        mMapActivity.handleWeatherInfo(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 获取城市信息
     *
     * @param location location
     */
    public void getCityInfo(String location) {
        Observable<Response> observable = HttpUtil.getGeoService().getCityInfo(location);
        observable.observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response response) {
                        mMapActivity.handleCityName(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
