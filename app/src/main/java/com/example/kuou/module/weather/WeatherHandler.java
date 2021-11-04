package com.example.kuou.module.weather;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.kuou.R;
import com.example.kuou.base.Constants;
import com.example.kuou.module.weather.model.AirNowConditionResponse;
import com.example.kuou.module.weather.model.DailyResponse;
import com.example.kuou.module.weather.model.LifestyleResponse;
import com.example.kuou.module.weather.model.NowResponse;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author JonesYang
 * @Date 2021-10-25
 * @Description
 */
public class WeatherHandler extends Handler {

    // 持有当前 Context 的引用
    private WeakReference<WeatherActivity> mWeatherActivityWeakReference;
    private WeatherActivity mMWeatherActivity;
    // 天气预报
    private List<DailyResponse.DailyBean> dailyForecastList = new ArrayList<>();
    private TextView mForecastDate;
    private TextView mForecastDay;
    private TextView mForecastNight;
    private TextView mForecastWinDirection;
    private TextView mForecastTemperature;
    // 生活建议
    private List<LifestyleResponse.DailyBean> dailySuggestionList = new ArrayList<>();
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
            case Constants.POST_BACKGROUND_IMAGE:
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
            case Constants.POST_NOW_WEATHER_DATA:
                // 设置当前天气数据
                handleNowWeatherData((NowResponse) msg.obj);
                break;
            case Constants.POST_FORECAST_WEATHER_DATA:
                // 设置预报天气数据
                handleForecastWeatherData((DailyResponse) msg.obj);
                break;
            case Constants.POST_AIR_CONDITION_DATA:
                // 空气质量
                handleAirConditionData((AirNowConditionResponse) msg.obj);
                break;
            case Constants.POST_LIFE_SUGGESTION:
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
        if (lifestyleResponse.getDaily() == null) {
            return;
        }
        // 处理数据重复的问题
        this.dailySuggestionList = lifestyleResponse.getDaily();
        mMWeatherActivity.mLlSuggestion.removeAllViews();

        // 需要对生活建议进行特殊的处理，
        int suggestionSize = dailySuggestionList.size();
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
        if (airNowConditionResponse == null || airNowConditionResponse.getNow() == null) {
            return;
        }
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
        if (dailyResponse == null || dailyResponse.getDaily() == null) {
            return;
        }
        // 解决数据重复添加的问题
        this.dailyForecastList = dailyResponse.getDaily();
        mMWeatherActivity.mForecastWeatherContainer.removeAllViews();

        for (int i = 0; i < dailyForecastList.size(); i++) {
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
            mForecastTemperature.setText(dailyResponse.getDaily().get(i).getTempMax() + "℃" + "~" + dailyResponse.getDaily().get(i).getTempMin() + "℃");
            mMWeatherActivity.mForecastWeatherContainer.addView(view);
        }
    }

    /**
     * 更新当前的天气数据
     *
     * @param nowResponse
     */
    public void handleNowWeatherData(@NotNull NowResponse nowResponse) {
        if (nowResponse.getNow() == null || nowResponse.getUpdateTime().isEmpty()) {
            return;
        }
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