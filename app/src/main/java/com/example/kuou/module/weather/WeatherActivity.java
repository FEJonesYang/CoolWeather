package com.example.kuou.module.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.example.kuou.R;
import com.example.kuou.base.Constants;
import com.example.kuou.common.message.HotCityEventMessage;
import com.example.kuou.common.message.LocationEventMessage;
import com.example.kuou.common.net.HttpUtil;
import com.example.kuou.common.utils.UIUtil;
import com.example.kuou.databinding.ActivityWeatherBinding;
import com.example.kuou.module.search.SearchCityActivity;
import com.example.kuou.module.search.adapter.SearchCityRecycleViewAdapter;
import com.example.kuou.module.search.model.SearchCityBean;
import com.example.kuou.module.weather.interfaces.WeatherDataCallback;
import com.example.kuou.module.weather.model.AirNowConditionResponse;
import com.example.kuou.module.weather.model.DailyResponse;
import com.example.kuou.module.weather.model.LifestyleResponse;
import com.example.kuou.module.weather.model.NowResponse;
import com.example.kuou.module.weather.model.WarmNowCityListResponse;
import com.example.kuou.module.weather.model.WarmNowResponse;
import com.example.kuou.module.weather.presenter.WeatherPresenter;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class WeatherActivity extends AppCompatActivity implements SearchCityRecycleViewAdapter.ISendSearchCityDataToWeatherActivityListener, WeatherDataCallback {

    private static final String TAG = "WeatherActivity";


    private ScrollView weatherLayout;

    // 这是 id 是用来获取提拉起数据的 id，定位时用的是经度纬度，城市搜索时用的是 locationId
    private String locationId;
    private String cityName;
    // 整个的定位数据
    private AMapLocation aMapLocation;

    public TextView degreeText, weatherInfoText;


    //更新天气控件
    public SwipeRefreshLayout swipeRefreshLayout;

    //滑动菜单
    private ImageView navMap;

    // 线程切换的Handler
    private WeatherHandler mWeatherHandler = new WeatherHandler(this);

    // 加载必应背景图
    public LinearLayout mLinearLayout;
    // 天气展示数据的 Presenter
    private WeatherPresenter mWeatherPresenter;
    private ActivityWeatherBinding mActivityWeatherBinding;

    /**
     * 持有的数据实体
     */
    public TextView mWinDirection;
    public TextView mHumidity;
    public TextView mAirPressure;
    public TextView mUpdateTime;
    public LinearLayout mForecastWeatherContainer;
    public TextView mTvAqi;
    public TextView mTvPm25;
    public LinearLayout mMoreAirCondition;
    public LinearLayout mLlSuggestion;
    public LinearLayout mMoreLifeSuggestion;
    public TextView titleCity;


    @SuppressLint("LongLogTag")
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
        mActivityWeatherBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather);

        // 注册事件总线,用于接收在 MyApplication 获得的定位数据
        EventBus.getDefault().register(this);
        initView();
        initEvent();
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onStart() {
        super.onStart();
        getWeatherData();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {

        // 获取数据
        getWeatherData();

        // 重定位功能实现
        navMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationId = aMapLocation.getLongitude() + "," + aMapLocation.getLatitude();
                cityName = aMapLocation.getDistrict();
                getWeatherData();
            }
        });

        // 地图功能实现，长按触发
        navMap.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent("com.example.kuou.module.map.MapActivity");
                startActivity(intent);
                // 返回true，不再触发单次点击的事件
                return true;
            }
        });

        // 根据天气 id 刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWeatherData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        mActivityWeatherBinding.includeTitle.ivMoreChoose.setOnClickListener((view) -> {
            Intent intent = new Intent(this, SearchCityActivity.class);
            startActivity(intent);
        });


    }

    /**
     * 获取天气的数据
     */
    private void getWeatherData() {
        // 网络状况判断
        if (!HttpUtil.isNetWorkConnection(this)) {
            weatherLayout.setVisibility(View.GONE);
            UIUtil.showErrorView(this);
            UIUtil.showShortToast(this, "请检查网络状况");
            return;
        } else {
            weatherLayout.setVisibility(View.VISIBLE);
            UIUtil.hideErrorView(this);
        }
        // 注册事件监听，在搜索城市获得数据之后，点击单个item需要获取数据
        SearchCityRecycleViewAdapter.setISendSearchCityDataToWeatherActivityListener(this);

        /**
         * 实现接口回调的
         * */
        mWeatherPresenter = WeatherPresenter.getInstance();
        // 注册
        mWeatherPresenter.setWeatherDataCallback(this);
        // 背景图片
        mWeatherPresenter.requestBackgroundImage();
        // 实时天气数据
        mWeatherPresenter.requestNowWeatherData(locationId);
        // 天气预报
        mWeatherPresenter.requestForecast3DayData(locationId);
        // 空气质量
        mWeatherPresenter.requestAirConditionData(locationId);
        // 生活指数
        mWeatherPresenter.requestLifeConditionData(locationId);

        Toast.makeText(this, "数据更新成功", Toast.LENGTH_SHORT).show();

        // 设置名称
        titleCity.setText(cityName);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        // 获取这个页面中的最外面的布局
        mLinearLayout = findViewById(R.id.ll_weather);


        //地图搜索功能
        navMap = (ImageView) findViewById(R.id.nav_button);

        // 界面最上方的城市名称
        titleCity = mActivityWeatherBinding.includeTitle.titleCity;

        // 滑动布局
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);

        /**
         * 初始化视图控件
         * */
        //实时天气数据
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        mWinDirection = findViewById(R.id.tv_win_Info);
        mHumidity = findViewById(R.id.tv_humidity_Info);
        mAirPressure = findViewById(R.id.tv_air_pressure_Info);
        mUpdateTime = findViewById(R.id.tv_updateTime);

        // 天气预报
        mForecastWeatherContainer = findViewById(R.id.ll_forecast_container);

        // 生活质量,这里只有 aqi 和 pm2.5
        mTvAqi = findViewById(R.id.tv_api);
        mTvPm25 = findViewById(R.id.tv_pm25);
        mMoreAirCondition = findViewById(R.id.ll_more_weather_aqi);

        // 生活建议的布局
        mLlSuggestion = findViewById(R.id.ll_suggestion_container);
        mMoreLifeSuggestion = findViewById(R.id.ll_more_weather_suggestion);


        //更新天气的处理逻辑
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }


    /**
     * 实现接口回调，搜索界面的数据传到这，可日志打印
     *
     * @param locationBean
     */
    @Override
    public void postSearchCityData(SearchCityBean.LocationBean locationBean) {
        this.cityName = locationBean.getName();
        this.locationId = locationBean.getId();
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }

    /**
     * 实时天气数据查询的
     *
     * @param nowResponse
     */
    @Override
    public void nowWeatherDataCallback(NowResponse nowResponse) {
        Message message = mWeatherHandler.obtainMessage();
        message.what = Constants.POST_NOW_WEATHER_DATA;
        message.obj = nowResponse;
        mWeatherHandler.sendMessage(message);
    }

    /**
     * 生活建议的数据回调
     *
     * @param lifestyleResponse
     */
    @Override
    public void lifeConditionDataCallback(LifestyleResponse lifestyleResponse) {
        Message message = mWeatherHandler.obtainMessage();
        message.what = Constants.POST_LIFE_SUGGESTION;
        message.obj = lifestyleResponse;
        mWeatherHandler.sendMessage(message);
    }

    /**
     * 空气质量数据处理
     *
     * @param airNowConditionResponse
     */
    @Override
    public void airConditionDataCallback(AirNowConditionResponse airNowConditionResponse) {
        Message message = mWeatherHandler.obtainMessage();
        message.what = Constants.POST_AIR_CONDITION_DATA;
        message.obj = airNowConditionResponse;
        mWeatherHandler.sendMessage(message);
    }

    /**
     * 天气预报相关的
     *
     * @param dailyResponse
     */
    @Override
    public void weatherForecastDataCallback(DailyResponse dailyResponse) {
        Message message = mWeatherHandler.obtainMessage();
        message.what = Constants.POST_FORECAST_WEATHER_DATA;
        message.obj = dailyResponse;
        mWeatherHandler.sendMessage(message);
    }

    @Override
    public void warmNowDataCallback(WarmNowResponse warmNowResponse) {

    }

    @Override
    public void warmNowCityListDataCallback(WarmNowCityListResponse warmNowCityListResponse) {

    }

    /**
     * 背景图片的处理
     *
     * @param url
     */
    @Override
    public void loadImageUrlDataCallback(String url) {
        Message message = mWeatherHandler.obtainMessage();
        message.what = Constants.POST_BACKGROUND_IMAGE;
        message.obj = url;
        mWeatherHandler.sendMessage(message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(LocationEventMessage event) {
        this.aMapLocation = event.getAMapLocation();
        this.cityName = event.getAMapLocation().getDistrict();
        this.locationId = event.getAMapLocation().getLongitude() + "," + event.getAMapLocation().getLatitude();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHotCityMessageEvent(HotCityEventMessage event) {
        this.locationId = event.getHotCityResponse().getTopCityList().get(event.getIndex()).getId();
        this.cityName = event.getHotCityResponse().getTopCityList().get(event.getIndex()).getName();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().removeStickyEvent(LocationEventMessage.class);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
