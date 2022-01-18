package com.jonesyong.module_main;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jonesyong.library_common.utils.CommonUtil;

import org.greenrobot.eventbus.EventBus;


/**
 * @Author JonesYang
 * @Date 2022-01-14
 * @Description 获取定位信息
 */
public class LocationService extends Service {

    private static final String TAG = "LocationService";

    //声明AMapLocationClient类对象
    private static AMapLocationClient mLocationClient;
    //声明定位回调监听器
    private static AMapLocationListener mLocationListener;

    //声明AMapLocationClientOption对象--AMapLocationClientOption对象用来设置发起定位的模式和相关参数。
    private static AMapLocationClientOption mLocationOption = new AMapLocationClientOption();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;// 启动服务。返回 null 即可
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mLocationClient = new AMapLocationClient(this);
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        boolean isSucceed = true;
        mLocationListener = (location) -> {
            if (location != null) {
                if (location.getErrorCode() == 0) {
                    // 数据先缓存
                    String cityName = location.getDistrict();
                    String locationId = location.getLongitude() + "," + location.getLatitude();
                    CommonUtil.putLocationToCache(this, locationId, cityName);
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e(TAG, "location Error, ErrCode:"
                            + location.getErrorCode() + ", errInfo:"
                            + location.getErrorInfo());

                }
            }
        };
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        mLocationClient.setLocationListener(mLocationListener);
        return super.onStartCommand(intent, flags, startId);
    }
}
