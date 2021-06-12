package com.example.kuou.module.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.kuou.R;
import com.example.kuou.module.weather.model.NowResponse;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.ref.WeakReference;

public class MapActivity extends AppCompatActivity implements AMap.OnMyLocationChangeListener, AMap.OnMapClickListener, MapWeatherPresenter.ISendMapWeatherListener {

    /// 地图相关的
    private static final String TAG = "MapActivity";
    MapView mMapView = null;
    AMap aMap = null;

    // 天气信息的,这里用的是经度纬度信息
    private String weatherId;
    // 线程切换的handler
    private MapWeatherHandler mapWeatherHandler = new MapWeatherHandler(this);
    private static final int POST_MAP_WEATHER_DATA = 1;
    private static final int POST_CITY_NAME = 2;

    private NowResponse nowResponse;
    private MapWeatherPresenter mapWeatherPresenter = MapWeatherPresenter.getInstance();

    // 视图
    private TextView cityName;
    private TextView cityTemperature;
    private TextView weatherStatus;
    private ImageView weatherStatusIcon;
    private TextView aqiInfo;
    private TextView pressure;
    private TextView windDir;
    private TextView humidity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        // 展示地图蓝色的点
        showBluePoint();

        // 定位时获取位置
        aMap.setOnMyLocationChangeListener(this);

        // 设置手势点击，获取点击的位置信息
        aMap.setOnMapClickListener(this);

        // 数据回调
        MapWeatherPresenter.getInstance().setMapWeatherListener(this);

        initView();

    }

    /**
     * 初始化视图
     */
    private void initView() {
        cityName = findViewById(R.id.tv_map_location_city_name);
        cityTemperature = findViewById(R.id.tv_map_location_city_temperature);
        weatherStatus = findViewById(R.id.tv_map_status);
        weatherStatusIcon = findViewById(R.id.iv_map_status);
        aqiInfo = findViewById(R.id.tv_map_api);

        pressure = findViewById(R.id.tv_map_pressure);
        windDir = findViewById(R.id.tv_map_windDir);
        humidity = findViewById(R.id.tv_map_humidity);
    }

    /**
     * 请求数据
     */
    private void changeData() {
        mapWeatherPresenter.requestNowWeatherData(weatherId);
        mapWeatherPresenter.queryLocationCity(weatherId);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 展示地图蓝点
     */
    private void showBluePoint() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
    }

    /**
     * 获得定位监听的位置
     *
     * @param location
     */
    @SuppressLint("LongLogTag")
    @Override
    public void onMyLocationChange(Location location) {
        this.weatherId = location.getLongitude() + "," + location.getLatitude();
        changeData();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onMapClick(LatLng latLng) {
        this.weatherId = latLng.longitude + "," + latLng.latitude;
        changeData();
    }

    /**
     * 需要考虑线程切换的问题
     *
     * @param nowResponse
     */
    @Override
    public void postMapWeatherData(NowResponse nowResponse) {
        Message message = mapWeatherHandler.obtainMessage();
        message.what = POST_MAP_WEATHER_DATA;
        message.obj = nowResponse;
        mapWeatherHandler.sendMessage(message);
    }

    /**
     * @param name 城市名称
     */
    @Override
    public void postCityName(String name) {
        Message message = mapWeatherHandler.obtainMessage();
        message.what = 2;
        message.obj = name;
        mapWeatherHandler.sendMessage(message);
    }

    /**
     * 改变 UI ，展示天气的数据
     *
     * @param nowResponse
     */
    private void handleWeatherInfo(NowResponse nowResponse) {
        NowResponse.NowBean nowBean = nowResponse.getNow();
        if (nowBean != null) {
            cityTemperature.setText(nowBean.getTemp() + "℃");
            weatherStatus.setText(nowBean.getText());
            aqiInfo.setText("AQI 指数: " + nowBean.getCloud());
            pressure.setText("气压: " + nowBean.getPressure());
            windDir.setText("风向: " + nowBean.getWindDir());
            humidity.setText("湿度: " + nowBean.getHumidity());

            // 设置图片
            String imgUrl = "R.drawable.icon_" + nowBean.getIcon() + ".png";
            int resID = getResources().getIdentifier(imgUrl, "drawable", getPackageName());
            weatherStatusIcon.setImageResource(resID);
        }
    }

    /**
     * 处理城市的名称
     *
     * @param name
     */
    private void handleCityName(String name) {
        if (name != null) {
            cityName.setText(name);
        }
    }

    private static class MapWeatherHandler extends Handler {
        private WeakReference<MapActivity> mapActivityWeakReference;

        public MapWeatherHandler(MapActivity activity) {
            mapActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull @NotNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                // 处理天气信息的展示
                case POST_MAP_WEATHER_DATA:
                    mapActivityWeakReference.get().handleWeatherInfo((NowResponse) msg.obj);
                    break;
                //城市名称
                case POST_CITY_NAME:
                    mapActivityWeakReference.get().handleCityName((String) msg.obj);
                    break;
            }
        }
    }
}