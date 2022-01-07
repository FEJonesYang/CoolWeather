package com.jonesyong.module_search;

/**
 * @Author JonesYang
 * @Date 2021-12-15
 * @Description
 */


import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;


import com.jonesyong.library_common.base.Constants;
import com.jonesyong.library_common.model.HotCityResponse;
import com.jonesyong.library_common.model.SearchCityBean;

import java.lang.ref.WeakReference;

/**
 * 处理搜索城市列表的线程切换
 */
public class SearchCityHandler extends Handler {

    private final WeakReference<SearchCityActivity> mWeakReference;

    public SearchCityHandler(SearchCityActivity activity) {
        mWeakReference = new WeakReference<>(activity);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            // 处理城市信息查询的
            case Constants.POST_SEARCH_CITY_DATA:
                mWeakReference.get().mSearchCityPresenter.mAdapter.setSearchCityData((SearchCityBean) msg.obj);
                break;
            // 处理热门城市的数据
            case Constants.POST_HOT_CITY_DATA:
                mWeakReference.get().handleHotCity((HotCityResponse) msg.obj);
                break;
        }
    }
}
