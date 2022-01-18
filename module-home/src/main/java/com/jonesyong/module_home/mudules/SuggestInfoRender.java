package com.jonesyong.module_home.mudules;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jonesyong.library_common.model.DailyModel;
import com.jonesyong.library_common.model.Response;
import com.jonesyong.module_home.R;
import com.jonesyong.module_home.WeatherActivity;


import java.util.List;

/**
 * @Author JonesYang
 * @Date 2022-01-10
 * @Description
 */
public class SuggestInfoRender {

    private WeatherActivity mWeatherActivity;

    public SuggestInfoRender(WeatherActivity weatherActivity) {
        this.mWeatherActivity = weatherActivity;
    }

    /**
     * 生活建议的数据处理
     *
     * @param lifestyleResponse base response
     */
    public void handleLifeSuggestion(Response lifestyleResponse) {
        if (lifestyleResponse.getDaily() == null) {
            return;
        }
        // 处理数据重复的问题
        // 生活建议
        List<DailyModel> dailySuggestionList = lifestyleResponse.getDaily();
        mWeatherActivity.mLlSuggestion.removeAllViews();

        // 需要对生活建议进行特殊的处理，
        int suggestionSize = dailySuggestionList.size();
        TextView suggestionLevel;
        TextView suggestionName;
        TextView suggestText;
        if (suggestionSize > 3) {
            // 超过了3条数据,但是只显示3条数据
            for (int i = 0; i < 3; i++) {
                View view = LayoutInflater.from(mWeatherActivity).
                        inflate(R.layout.item_suggestion, null, false);
                suggestionName = view.findViewById(R.id.tv_suggestion_name);
                suggestionLevel = view.findViewById(R.id.tv_suggestion_level);
                suggestText = view.findViewById(R.id.tv_suggestion_text);
                // 设置数据
                suggestionName.setText(lifestyleResponse.getDaily().get(i).getName());
                suggestionLevel.setText(lifestyleResponse.getDaily().get(i).getCategory());
                suggestText.setText(lifestyleResponse.getDaily().get(i).getText());
                mWeatherActivity.mLlSuggestion.addView(view);
                // 解决每一个 item 的间距问题
                TextView m = new TextView(mWeatherActivity);
                m.setHeight(25);
                mWeatherActivity.mLlSuggestion.addView(m);
            }

        } else {
            for (int i = 0; i < suggestionSize; i++) {
                View view = LayoutInflater.from(mWeatherActivity).
                        inflate(R.layout.item_suggestion, null, false);
                suggestionName = view.findViewById(R.id.tv_suggestion_name);
                suggestionLevel = view.findViewById(R.id.tv_suggestion_level);
                suggestText = view.findViewById(R.id.tv_suggestion_text);
                // 设置数据
                suggestionName.setText(lifestyleResponse.getDaily().get(i).getName());
                suggestionLevel.setText(lifestyleResponse.getDaily().get(i).getCategory());
                suggestText.setText(lifestyleResponse.getDaily().get(i).getText());
                mWeatherActivity.mLlSuggestion.addView(view);
                // 解决每一个 item 的间距问题
                TextView m = new TextView(mWeatherActivity);
                m.setHeight(25);
                mWeatherActivity.mLlSuggestion.addView(m);
            }
        }
    }
}
