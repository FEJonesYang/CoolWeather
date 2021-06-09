package com.example.kuou.module.search;

import android.util.Log;

import com.example.kuou.base.Constants;
import com.example.kuou.common.json.Utility;
import com.example.kuou.common.net.Api;
import com.example.kuou.common.net.HttpUtil;
import com.example.kuou.module.search.model.HotCityResponse;
import com.example.kuou.module.search.model.SearchCityBean;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author JonesYang
 * @Data 2021-05-28
 * @Function 搜索城市的数据提供者
 */
public class SearchCityPresenter {
    private static final String TAG = "SearchCityPresenter";

    // 单例模式
    private static SearchCityPresenter instance;

    private SearchCityPresenter() {
    }

    public static SearchCityPresenter getInstance() {
        if (instance == null) {
            synchronized (SearchCityPresenter.class) {
                if (instance == null) {
                    instance = new SearchCityPresenter();
                }
            }
        }
        return instance;
    }

    // 查询城市
    public void queryLocation(String location) {
        HttpUtil.sendOkHttpRequest(Api.queryCity + location, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                SearchCityBean searchCityBean = gson.fromJson(response.body().string(), SearchCityBean.class);
                mISendSearchCityListener.PostSearchData(searchCityBean);
                Log.d(TAG, searchCityBean.toString());
            }
        });
    }

    // 查询热门城市
    public void queryHotLocation() {
        HttpUtil.sendOkHttpRequest(Api.queryHotCity, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                HotCityResponse hotCityResponse = Utility.getGsonInstance().fromJson(response.body().string(), HotCityResponse.class);
                mISendSearchCityListener.postHotCityData(hotCityResponse);
                Log.d(TAG, hotCityResponse.toString());
            }
        });
    }

    // 接口实现数据传递到SearchCityActivity
    private ISendSearchCityListener mISendSearchCityListener;

    public void setISendSearchCityListener(ISendSearchCityListener ISendSearchCityListener) {
        mISendSearchCityListener = ISendSearchCityListener;
    }

    public interface ISendSearchCityListener {
        // 城市搜索数据
        void PostSearchData(SearchCityBean searchCityBean);

        // 热门城市数据
        void postHotCityData(HotCityResponse hotCityResponse);
    }


}
