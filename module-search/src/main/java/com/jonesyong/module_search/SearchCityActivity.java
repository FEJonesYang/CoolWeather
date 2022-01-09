package com.jonesyong.module_search;


import androidx.annotation.RequiresApi;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jonesyong.library_common.base.BaseActivity;
import com.jonesyong.library_common.base.Constants;
import com.jonesyong.library_common.base.Router;
import com.jonesyong.library_common.net.HttpUtil;
import com.jonesyong.library_common.model.HotCityResponse;
import com.jonesyong.library_common.model.SearchCityBean;

@Route(path = Router.MODULE_SEARCH_SEARCH_ACTIVITY)
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
        message.what = Constants.POST_SEARCH_CITY_DATA;
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
        message.what = Constants.POST_HOT_CITY_DATA;
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

    public void showErrorPage() {
        mSearchCityPresenter.showErrorView();
    }

    public void showEmptyPage() {
        mSearchCityPresenter.showEmptyView();
    }
}