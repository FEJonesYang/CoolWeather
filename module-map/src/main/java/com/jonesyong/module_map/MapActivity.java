package com.jonesyong.module_map;


import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.jonesyong.library_common.base.BaseActivity;
import com.jonesyong.library_common.base.Router;
import com.jonesyong.library_common.model.LocationModel;
import com.jonesyong.library_common.model.NowModel;
import com.jonesyong.library_common.model.Response;


@Route(path = Router.MODULE_MAP_MAP_ACTIVITY)
public class MapActivity extends BaseActivity implements AMap.OnMyLocationChangeListener, AMap.OnMapClickListener {

    /// 地图相关的
    private static final String TAG = "MapActivity";
    MapView mMapView = null;
    AMap aMap = null;
    // 天气信息的,这里用的是经度纬度信息
    private String weatherId;

    private MapWeatherPresenter mapWeatherPresenter = new MapWeatherPresenter(this);

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //获取地图控件引用
        mMapView = findViewById(R.id.map);
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
        mapWeatherPresenter.getCityInfo(weatherId);
        mapWeatherPresenter.getNowWeatherInfo(weatherId);
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
     * 改变 UI ，展示天气的数据
     *
     * @param response base response
     */
    @SuppressLint("SetTextI18n")
    public void handleWeatherInfo(Response response) {
        NowModel nowBean = response.getNow();
        if (nowBean != null) {
            cityTemperature.setText(nowBean.getTemp() + "℃");
            weatherStatus.setText(nowBean.getText());
            aqiInfo.setText("AQI 指数: " + nowBean.getCloud());
            pressure.setText("气压: " + nowBean.getPressure());
            windDir.setText("风向: " + nowBean.getWindDir());
            humidity.setText("湿度: " + nowBean.getHumidity());
        }
    }

    /**
     * 处理城市的名称
     *
     * @param response base response
     */
    public void handleCityName(Response response) {
        LocationModel model = response.getLocation().get(0);
        if (model != null) {
            cityName.setText(model.getName());
        }
    }
}