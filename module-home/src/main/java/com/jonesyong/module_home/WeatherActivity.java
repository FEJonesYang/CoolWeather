package com.jonesyong.module_home;

import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jonesyong.library_common.base.BaseActivity;
import com.jonesyong.library_common.base.Router;
import com.jonesyong.library_common.message.HotCityEventMessage;
import com.jonesyong.library_common.message.SearchCityEvent;
import com.jonesyong.library_common.utils.ARouterUtils;
import com.jonesyong.library_common.utils.CommonUtil;
import com.jonesyong.module_home.databinding.ActivityWeatherBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Route(path = Router.MODULE_HOME_WEATHER_ACTIVITY)
public class WeatherActivity extends BaseActivity {

    private static final String TAG = "WeatherActivity";

    // 这是 id 是用来获取提拉起数据的 id，定位时用的是经度纬度，城市搜索时用的是 locationId
    @Autowired
    String locationId;
    @Autowired
    String cityName;
    //更新天气控件
    public SwipeRefreshLayout swipeRefreshLayout;
    private ImageView navMap;
    // 加载必应背景图
    public LinearLayout mLinearLayout;
    public TextView titleCity;
    // 天气展示数据的 Presenter
    private WeatherPresenter mWeatherPresenter;
    private ActivityWeatherBinding mActivityWeatherBinding;
    // 各个模块的数据view
    public TextView degreeText, weatherInfoText;
    public TextView mWinDirection;
    public TextView mHumidity;
    public TextView mAirPressure;
    public TextView mUpdateTime;
    public LinearLayout mForecastWeatherContainer;
    public TextView mTvAqi;
    public TextView mTvPm25;
    public LinearLayout mLlSuggestion;
    private LinearLayout mMoreLifeSuggestion;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityWeatherBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather);
        // 注册事件总线,用于接收在 MyApplication 获得的定位数据
        EventBus.getDefault().register(this);
        initView();
        initEvent();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        mWeatherPresenter = new WeatherPresenter(this);
        // 获取数据
        getWeatherData();

        // 重定位功能实现
        navMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationId = CommonUtil.getLocationIdFromCache(getApplicationContext());
                cityName = CommonUtil.getCityNameFromCache(getApplicationContext());
                getWeatherData();
            }
        });

        // 地图功能实现，长按触发
        navMap.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ARouterUtils.navigationToMap();
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
            ARouterUtils.navigationToSearch();
        });
        // 更多生活建议
        mMoreLifeSuggestion.setOnClickListener(v -> {
            ARouter.getInstance().build(Router.MODULE_DETAIL_DETAIL_ACTIVITY)
                    .withString("location", this.locationId)
                    .withString("cityName", this.cityName)
                    .navigation();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWeatherData();
    }

    /**
     * 获取天气的数据
     */
    private void getWeatherData() {
        // 背景图片
        mWeatherPresenter.getBackgroundImage();
        // 实时天气数据
        mWeatherPresenter.getNowWeatherInfo(locationId);
        // 天气预报
        mWeatherPresenter.getForecastInfo(locationId);
        // 空气质量
        mWeatherPresenter.getAirQualityInfo(locationId);
        // 生活指数
        mWeatherPresenter.getSuggestionInfo(locationId);
        // 设置名称
        titleCity.setText(cityName);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        // 获取这个页面中的最外面的布局
        mLinearLayout = findViewById(R.id.ll_weather);
        //地图搜索功能
        navMap = findViewById(R.id.nav_button);
        // 界面最上方的城市名称
        titleCity = mActivityWeatherBinding.includeTitle.titleCity;
        //更新天气的处理逻辑
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        //实时天气数据
        degreeText = findViewById(R.id.degree_text);
        weatherInfoText = findViewById(R.id.weather_info_text);
        mWinDirection = findViewById(R.id.tv_win_Info);
        mHumidity = findViewById(R.id.tv_humidity_Info);
        mAirPressure = findViewById(R.id.tv_air_pressure_Info);
        mUpdateTime = findViewById(R.id.tv_updateTime);
        // 天气预报
        mForecastWeatherContainer = findViewById(R.id.ll_forecast_container);
        // 生活质量,这里只有 aqi 和 pm2.5
        mTvAqi = findViewById(R.id.tv_api);
        mTvPm25 = findViewById(R.id.tv_pm25);
        // 生活建议的布局
        mLlSuggestion = findViewById(R.id.ll_suggestion_container);
        mMoreLifeSuggestion = findViewById(R.id.ll_more_weather_suggestion);
    }


    /**
     * 实现接口回调，搜索界面的数据传到这，可日志打印
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void postSearchCityData(SearchCityEvent event) {
        this.cityName = event.getLocationModel().getName();
        this.locationId = event.getLocationModel().getId();
    }

    /**
     * 背景图片的处理
     *
     * @param url 图片完整的 url
     */
    public void loadImageUrlDataCallback(String url) {
        // 设置背景图片
        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        mLinearLayout.setBackground(drawable);
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHotCityMessageEvent(HotCityEventMessage event) {
        this.locationId = event.getHotCityListModel().getId();
        this.cityName = event.getHotCityListModel().getName();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
