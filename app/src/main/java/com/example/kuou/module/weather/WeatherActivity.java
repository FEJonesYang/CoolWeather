package com.example.kuou.module.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.kuou.R;
import com.example.kuou.databinding.ActivityWeatherBinding;
import com.example.kuou.gson.Forecast;
import com.example.kuou.gson.Weather;
import com.example.kuou.module.search.SearchCityActivity;
import com.example.kuou.module.search.adapter.SearchCityRecycleViewAdapter;
import com.example.kuou.module.search.model.SearchCityBean;
import com.example.kuou.module.weather.interfaces.WeatherDataCallback;
import com.example.kuou.module.weather.model.AirNowResponse;
import com.example.kuou.module.weather.model.DailyResponse;
import com.example.kuou.module.weather.model.LifestyleResponse;
import com.example.kuou.module.weather.model.NowResponse;
import com.example.kuou.module.weather.model.WarmNowCityListResponse;
import com.example.kuou.module.weather.model.WarmNowResponse;
import com.example.kuou.service.AutoUpdateService;
import com.example.kuou.common.net.HttpUtil;
import com.example.kuou.common.json.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity implements SearchCityRecycleViewAdapter.ISendSearchCityDataToWeatherActivityListener, WeatherDataCallback {

    private static final String TAG = "WeatherActivity";
    private ScrollView weatherLayout;

    private TextView titleCity, titleUpdateTime, degreeText, weatherInfoText, aqiText, pm25text, comfortText, carWashText, sportText;

    private LinearLayout forecastLayout;
    //加载必应背景图
    private ImageView bingPicImg;
    //更新天气控件
    public SwipeRefreshLayout swipeRefreshLayout;
    private String mWeatherId;

    //滑动菜单
    public DrawerLayout drawerLayout;
    private ImageView navMap;


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
        ActivityWeatherBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_weather);

        //滑动菜单的逻辑处理
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //地图搜索功能
        navMap = (ImageView) findViewById(R.id.nav_button);
        navMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO:地图业务
            }
        });


        //更新天气的处理逻辑
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        //获取天气缓存
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = preferences.getString("weather", null);


        //初始化各控件
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25text = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);


        //背景图片相关操作
        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        //从缓存中获取图片的 url
        String bingPic = preferences.getString("bing_pic", null);
        if (bingPic != null) {
            //加载图片
            loadBingPic();
            Glide.with(this).load(bingPic).into(bingPicImg);
        } else {
            //如果不存在图片的 url
            loadBingPic();
        }

        if (weatherString != null) {
            //有缓存是直接解析天气的数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {  //如果缓存中不存在数据
            // 获取 Activity 跳转是传递过来的 weather_id
            mWeatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            //根据 weather_id 请求数据
            requestWeather(mWeatherId);

        }
        // 根据天气 id 刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });

        // TODO 标题栏选择更多按钮，有时间写一个自定义的View
        binding.includeTitle.ivMoreChoose.setOnClickListener((view) -> {
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

        // 注册事件监听，在搜索城市获得数据之后，点击单个item需要获取数据
        SearchCityRecycleViewAdapter.setISendSearchCityDataToWeatherActivityListener(this);

    }


    /**
     * 加载必应每日一图
     */
    @Deprecated
    public void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                //加载失败
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "加载壁纸出错", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                // 把获取的数据转化为字符串
                final String bingPic = response.body().string();
                // 把获取的字符串存储在缓存中
                SharedPreferences.Editor editor = PreferenceManager
                        .getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                //在主线程更新 UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }


    /**
     * 根据天气的id请求城市天气信息
     */
    @Deprecated
    public void requestWeather(final String weatherId) {

        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=575a4dd45c9f46ca833a259f953c76b3";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气数据失败", Toast.LENGTH_SHORT).show();
                        //停止刷新
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //如果获取天气数据成功
                final String responseText = response.body().string();
                //把获取的数据先解析成 JSON 数据，再把 JSON 解析成 Weather
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //获取数据成功
                        if (weather != null && "ok".equals(weather.status)) {
                            //先把天气数据缓存起来
                            SharedPreferences.Editor editor = PreferenceManager.
                                    getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            mWeatherId = weather.basic.weatherId;
                            //展示获取的数据
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气数据失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    /**
     * 处理并展示Weather实体类的数据
     */
    @Deprecated
    public void showWeatherInfo(Weather weather) {

        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();

        //获取预报信息
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            forecastLayout.addView(view);
        }

        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.city.aqi);
            pm25text.setText(weather.aqi.city.pm25);
        }
        String comfort = "舒适度" + weather.suggestion.comfort.info;
        String carWash = "洗车指数" + weather.suggestion.carWash.info;
        String sport = "运动指数" + weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);

        //激活AutoUpdateService
        weatherLayout.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void NowWeatherDataCallback(NowResponse nowResponse) {

    }

    @Override
    public void LifeConditionDataCallback(LifestyleResponse lifestyleResponse) {

    }

    @Override
    public void AirConditionDataCallback(AirNowResponse airNowResponse) {

    }

    @Override
    public void AirConditionDataCallback(DailyResponse dailyResponse) {

    }

    @Override
    public void WarmNowDataCallback(WarmNowResponse warmNowResponse) {

    }

    @Override
    public void WarmNowCityListDataCallback(WarmNowCityListResponse warmNowCityListResponse) {

    }
}
