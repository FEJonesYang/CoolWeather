package com.jonesyong.module_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jonesyong.library_common.base.Router;
import com.jonesyong.library_common.utils.CommonUtil;
import com.jonesyong.library_common.utils.UIUtil;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;


/**
 * 做一个 Flash 界面，用于初始化系统配置，获取定位服务之类的额，
 * 为后面启动天气展示的界面做一个准备
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static int RequestPermissionCode = 1;


    //声明AMapLocationClient类对象
    private static AMapLocationClient mLocationClient;
    //声明定位回调监听器
    private static AMapLocationListener mLocationListener;

    //声明AMapLocationClientOption对象--AMapLocationClientOption对象用来设置发起定位的模式和相关参数。
    private static AMapLocationClientOption mLocationOption = new AMapLocationClientOption();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /// 定位权限的获取
        int hasPermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            //已获取权限
            getLocationAfterPermissionSuccess();
        } else {
            //未获取权限，需要弹出 dialog，这里自定义 View
            initDialogView();
        }
        // 启动服务获取定位
        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            new Thread(this::startLocationService).start();
        }
    }

    /**
     * 初始化 dialog 及相关的视图
     */
    private void initDialogView() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.widget_view_dialog, null, false);
        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(view);
        // 为确认、取消按钮添加点击事件
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
            UIUtil.showShortToast(this, "没有定位您将无法使用，3秒后自动退出");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 0);
            dialog.dismiss();
        });
        view.findViewById(R.id.btn_sure).setOnClickListener(v -> {
            dialog.dismiss();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RequestPermissionCode);
        });

    }

    /**
     * 权限申请的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RequestPermissionCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            UIUtil.showShortToast(this, "定位权限申请成功");
            getLocationAfterPermissionSuccess();
        } else {
            UIUtil.showShortToast(this, "定位权限申请失败");
        }
    }

    private void getLocationAfterPermissionSuccess() {
        // 从缓存中获取定位参数，如果不存在的话从Service请求
        String locationId = CommonUtil.getLocationIdFromCache(this);
        String cityName = CommonUtil.getCityNameFromCache(this);
        if (CommonUtil.isStringValid(locationId) && CommonUtil.isStringValid(cityName)) {
            ARouter.getInstance().build(Router.MODULE_HOME_WEATHER_ACTIVITY)
                    .withString("locationId", locationId)
                    .withString("cityName", cityName)
                    .navigation();
            finish();
        }
        mLocationClient = new AMapLocationClient(getApplicationContext());
        // 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationListener = (location) -> {
            if (location != null) {
                if (location.getErrorCode() == 0) {
                    //发送粘性事件
                    ARouter.getInstance().build(Router.MODULE_HOME_WEATHER_ACTIVITY)
                            .withString("locationId", location.getLongitude() + "," + location.getLatitude())
                            .withString("cityName", location.getDistrict())
                            .navigation();
                    finish();
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
    }

    private void startLocationService() {
        startService(new Intent(getApplicationContext(), LocationService.class));
    }
}
