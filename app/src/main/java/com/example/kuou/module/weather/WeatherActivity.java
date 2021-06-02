package com.example.kuou.module.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.kuou.R;
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


import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.lang.ref.WeakReference;


public class WeatherActivity extends AppCompatActivity implements SearchCityRecycleViewAdapter.ISendSearchCityDataToWeatherActivityListener, WeatherDataCallback {

    private static final String TAG = "WeatherActivity";

    /**
     * 线程切换相关的 what 标志
     */
    private static final int POST_BACKGROUND_IMAGE = 1;
    private static final int POST_NOW_WEATHER_DATA = 2;
    private static final int POST_FORECAST_WEATHER_DATA = 3;
    private static final int POST_AIR_CONDITION_DATA = 4;
    private static final int POST_LIFE_SUGGESTION = 5;

    private ScrollView weatherLayout;

    private TextView degreeText, weatherInfoText;


    //更新天气控件
    public SwipeRefreshLayout swipeRefreshLayout;

    //滑动菜单
    public DrawerLayout drawerLayout;
    private ImageView navMap;

    // 线程切换的Handler
    private WeatherHandler mWeatherHandler = new WeatherHandler(this);

    // 加载必应背景图
    private LinearLayout mLinearLayout;
    private WeatherPresenter mWeatherPresenter;
    private ActivityWeatherBinding mActivityWeatherBinding;

    /**
     * 持有的数据实体
     */
    private TextView mWinDirection;
    private TextView mHumidity;
    private TextView mAirPressure;
    private TextView mUpdateTime;
    private LinearLayout mForecastWeatherContainer;
    private TextView mTvAqi;
    private TextView mTvPm25;
    private TextView mMoreAirCondition;
    private LinearLayout mLlSuggestion;
    private TextView mMoreLifeSuggestion;


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

        initView();

        initEvent();

    }

    /**
     * 初始化事件
     */
    private void initEvent() {

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
        // 实时天气数据 TODO:先用北京的数据做测试，数据后面需要替换
        mWeatherPresenter.requestNowWeatherData("101010300");
        // 天气预报
        mWeatherPresenter.requestForecast3DayData("101010300");
        // 空气质量
        mWeatherPresenter.requestAirConditionData("101010300");
        // 生活指数
        mWeatherPresenter.requestLifeConditionData("101010300");

        navMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO:地图业务
            }
        });

        // 根据天气 id 刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO 重新获取数据
            }
        });

        // TODO 标题栏选择更多按钮，有时间写一个自定义的View
        mActivityWeatherBinding.includeTitle.ivMoreChoose.setOnClickListener((view) -> {
            //创建弹出式菜单对象（最低版本11）
            PopupMenu popup = new PopupMenu(this, view);//第二个参数是绑定的那个view
            //获取菜单填充器
            MenuInflater inflater = popup.getMenuInflater();
            //填充菜单
            inflater.inflate(R.menu.pop_menu, popup.getMenu());
            //绑定菜单项的点击事件
            popup.setOnMenuItemClickListener((item) -> {
                switch (item.getItemId()) {
                    case R.id.search_city:
                        Intent intent = new Intent(this, SearchCityActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.about_author:
                        //打开滑动抽屉
                        drawerLayout.openDrawer(GravityCompat.START);
                        break;
                }
                return false;
            });
            //显示(这一行代码不要忘记了)
            popup.show();

        });

    }

    /**
     * 初始化视图
     */
    private void initView() {
        // 获取这个页面中的最外面的布局
        mLinearLayout = findViewById(R.id.ll_weather);

        //滑动菜单的逻辑处理
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //地图搜索功能
        navMap = (ImageView) findViewById(R.id.nav_button);

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
        mMoreAirCondition = findViewById(R.id.tv_more_air_condition);

        // 生活建议的布局
        mLlSuggestion = findViewById(R.id.ll_suggestion_container);
        mMoreLifeSuggestion = findViewById(R.id.tv_more_life_suggestion);


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
        Log.d(TAG, locationBean.toString());
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
        message.what = POST_NOW_WEATHER_DATA;
        message.obj = nowResponse;
        mWeatherHandler.sendMessage(message);
        Log.d(TAG, nowResponse.toString());
    }

    /**
     * 生活建议的数据回调
     *
     * @param lifestyleResponse
     */
    @Override
    public void lifeConditionDataCallback(LifestyleResponse lifestyleResponse) {
        Message message = mWeatherHandler.obtainMessage();
        message.what = POST_LIFE_SUGGESTION;
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
        message.what = POST_AIR_CONDITION_DATA;
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
        message.what = POST_FORECAST_WEATHER_DATA;
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
        message.what = POST_BACKGROUND_IMAGE;
        message.obj = url;
        mWeatherHandler.sendMessage(message);
    }


    private static class WeatherHandler extends Handler {

        // 持有当前 Context 的引用
        private WeakReference<WeatherActivity> mWeatherActivityWeakReference;
        private WeatherActivity mMWeatherActivity;
        // 天气预报
        private TextView mForecastDate;
        private TextView mForecastDay;
        private TextView mForecastNight;
        private TextView mForecastWinDirection;
        private TextView mForecastTemperature;
        // 生活建议
        private TextView mSuggestText;
        private TextView mSuggestionLevel;
        private TextView mSuggestionName;

        public WeatherHandler(WeatherActivity weatherActivity) {
            mWeatherActivityWeakReference = new WeakReference<>(weatherActivity);
        }

        /**
         * 消息进行处理的地方
         *
         * @param msg
         */
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            mMWeatherActivity = mWeatherActivityWeakReference.get();
            switch (msg.what) {
                case POST_BACKGROUND_IMAGE:
                    // 设置背景图片
                    Glide.with(mWeatherActivityWeakReference.get())
                            .asBitmap()
                            .load(msg.obj)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                    Drawable drawable = new BitmapDrawable(resource);
                                    mWeatherActivityWeakReference.get().mLinearLayout.setBackground(drawable);
                                }
                            });
                    break;
                case POST_NOW_WEATHER_DATA:
                    // 设置当前天气数据
                    handleNowWeatherData((NowResponse) msg.obj);
                    break;
                case POST_FORECAST_WEATHER_DATA:
                    // 设置预报天气数据
                    handleForecastWeatherData((DailyResponse) msg.obj);
                    break;
                case POST_AIR_CONDITION_DATA:
                    // 空气质量
                    handleAirConditionData((AirNowConditionResponse) msg.obj);
                    break;
                case POST_LIFE_SUGGESTION:
                    // 生活建议
                    handleLifeSuggestion((LifestyleResponse) msg.obj);
                    break;
            }
        }

        /**
         * 生活建议的数据处理
         *
         * @param lifestyleResponse
         */
        private void handleLifeSuggestion(@NotNull LifestyleResponse lifestyleResponse) {
            // 需要对生活建议进行特殊的处理，
            int suggestionSize = lifestyleResponse.getDaily().size();
            if (suggestionSize > 3) {
                // 超过了3条数据,但是只显示3条数据
                for (int i = 0; i < 3; i++) {
                    View view = LayoutInflater.from(mMWeatherActivity).
                            inflate(R.layout.item_suggestion, null, false);
                    mSuggestionName = view.findViewById(R.id.tv_suggestion_name);
                    mSuggestionLevel = view.findViewById(R.id.tv_suggestion_level);
                    mSuggestText = view.findViewById(R.id.tv_suggestion_text);
                    // 设置数据
                    mSuggestionName.setText(lifestyleResponse.getDaily().get(i).getName());
                    mSuggestionLevel.setText(lifestyleResponse.getDaily().get(i).getCategory());
                    mSuggestText.setText(lifestyleResponse.getDaily().get(i).getText());
                    mMWeatherActivity.mLlSuggestion.addView(view);
                    // 解决每一个 item 的间距问题
                    TextView m = new TextView(mMWeatherActivity);
                    m.setHeight(25);
                    mMWeatherActivity.mLlSuggestion.addView(m);
                }

            } else {
                for (int i = 0; i < suggestionSize; i++) {
                    View view = LayoutInflater.from(mMWeatherActivity).
                            inflate(R.layout.item_suggestion, null, false);
                    mSuggestionName = view.findViewById(R.id.tv_suggestion_name);
                    mSuggestionLevel = view.findViewById(R.id.tv_suggestion_level);
                    mSuggestText = view.findViewById(R.id.tv_suggestion_text);
                    // 设置数据
                    mSuggestionName.setText(lifestyleResponse.getDaily().get(i).getName());
                    mSuggestionLevel.setText(lifestyleResponse.getDaily().get(i).getCategory());
                    mSuggestText.setText(lifestyleResponse.getDaily().get(i).getText());
                    mMWeatherActivity.mLlSuggestion.addView(view);
                    // 解决每一个 item 的间距问题
                    TextView m = new TextView(mMWeatherActivity);
                    m.setHeight(25);
                    mMWeatherActivity.mLlSuggestion.addView(m);
                }
            }
            // 进入更多的界面
            mMWeatherActivity.mMoreLifeSuggestion.setOnClickListener((l) -> {
                if (suggestionSize > 3) {
                    // TODO:进入生活建议更多的界面
                } else {
                    Toast.makeText(mMWeatherActivity, "且行且珍惜,没有更多的生活建议了哦", Toast.LENGTH_SHORT).show();
                }
            });
        }

        /**
         * 空气质量的处理，这里只有两个数据，更多数据需要点击进行路详情页面
         *
         * @param airNowConditionResponse
         */
        private void handleAirConditionData(AirNowConditionResponse airNowConditionResponse) {
            mMWeatherActivity.mTvAqi.setText(airNowConditionResponse.getNow().getAqi());
            mMWeatherActivity.mTvPm25.setText(airNowConditionResponse.getNow().getPm2p5());
            mMWeatherActivity.mMoreAirCondition.setOnClickListener((l) -> {
                //TODO:携带实体数据进入空气质量展示界面
            });
        }

        /**
         * 天气预报UI展示处理
         *
         * @param dailyResponse
         */
        private void handleForecastWeatherData(DailyResponse dailyResponse) {
            for (int i = 0; i < dailyResponse.getDaily().size(); i++) {
                View view = LayoutInflater.from(mMWeatherActivity).inflate(R.layout.item_forecast, null, false);
                mForecastDate = view.findViewById(R.id.tv_forecast_date);
                mForecastDay = view.findViewById(R.id.tv_forecast_day);
                mForecastNight = view.findViewById(R.id.tv_forecast_night);
                mForecastWinDirection = view.findViewById(R.id.tv_forecast_win_direction);
                mForecastTemperature = view.findViewById(R.id.tv_forecast_temperature);
                // 设置数据
                mForecastDate.setText(dailyResponse.getDaily().get(i).getFxDate());
                mForecastDay.setText(dailyResponse.getDaily().get(i).getTextDay());
                mForecastNight.setText(dailyResponse.getDaily().get(i).getTextNight());
                mForecastWinDirection.setText(dailyResponse.getDaily().get(i).getWindDirDay());
                mForecastTemperature.setText(dailyResponse.getDaily().get(i).getTempMax() + "℃/" +
                        dailyResponse.getDaily().get(i).getTempMin() + "℃/");
                mMWeatherActivity.mForecastWeatherContainer.addView(view);
            }
        }

        /**
         * 更新当前的天气数据
         *
         * @param nowResponse
         */
        public void handleNowWeatherData(@NotNull NowResponse nowResponse) {
            // 温度
            mMWeatherActivity.degreeText.setText(nowResponse.getNow().getTemp() + "℃");
            // 天气状况
            mMWeatherActivity.weatherInfoText.setText(nowResponse.getNow().getText());
            // 风向
            mMWeatherActivity.mWinDirection.setText(nowResponse.getNow().getWindDir());
            // 湿度
            mMWeatherActivity.mHumidity.setText(nowResponse.getNow().getHumidity());
            // 气压
            mMWeatherActivity.mAirPressure.setText(nowResponse.getNow().getPressure());
            // 更新时间
            mMWeatherActivity.mUpdateTime.setText("更新时间： " + nowResponse.getUpdateTime().substring(11, 16));
        }
    }


}
