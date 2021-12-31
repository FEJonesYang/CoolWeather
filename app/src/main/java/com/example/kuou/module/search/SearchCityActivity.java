package com.example.kuou.module.search;

import static com.example.kuou.base.Constants.POST_HOT_CITY_DATA;
import static com.example.kuou.base.Constants.POST_SEARCH_CITY_DATA;

import androidx.annotation.RequiresApi;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.example.kuou.base.BaseActivity;
import com.example.kuou.common.net.HttpUtil;
import com.example.kuou.module.search.model.HotCityResponse;
import com.example.kuou.module.search.model.SearchCityBean;

public class SearchCityActivity extends BaseActivity implements SearchCityPresenter.ISendSearchCityListener {

    private static final String TAG = "SearchCityActivity";
    public SearchCityHandler mSearchCityHandler;
    public SearchCityPresenter mSearchCityPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchCityHandler = new SearchCityHandler(this);
        mSearchCityPresenter = new SearchCityPresenter(this);
        mSearchCityPresenter.initDatabase();
        mSearchCityPresenter.initView();
        mSearchCityPresenter.initEvent();
        if (!HttpUtil.isNetWorkConnection(this)) {
            showErrorPage();
        }
    }


    /**
     * 数据回调，城市搜索数据
     *
     * @param searchCityBean
     */
    @Override
    public void PostSearchData(SearchCityBean searchCityBean) {
        Log.d(TAG, searchCityBean.toString());
        Message message = Message.obtain();
        message.obj = searchCityBean;
        message.what = POST_SEARCH_CITY_DATA;
        mSearchCityHandler.sendMessage(message);
    }

    /**
     * 热门城市的搜索数据
     *
     * @param hotCityResponse
     */
    @Override
    public void postHotCityData(HotCityResponse hotCityResponse) {
        Message message = Message.obtain();
        message.obj = hotCityResponse;
        message.what = POST_HOT_CITY_DATA;
        mSearchCityHandler.sendMessage(message);
    }

    /**
     * 展示热门城市的数据
     *
     * @param hotCityResponse
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void handleHotCity(HotCityResponse hotCityResponse) {
        mSearchCityPresenter.handleHotCity(hotCityResponse);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showErrorPage() {
        super.showErrorPage();
        mSearchCityPresenter.showErrorView();
    }

    @Override
    public void showEmptyPage() {
        super.showEmptyPage();
        mSearchCityPresenter.showEmptyView();
    }
}