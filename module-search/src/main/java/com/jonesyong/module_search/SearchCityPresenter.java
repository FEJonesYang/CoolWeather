package com.jonesyong.module_search;

import com.jonesyong.library_common.model.Response;
import com.jonesyong.library_common.net.HttpUtil;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author JonesYang
 * @Data 2021-05-28
 * @Function 搜索城市的数据提供者
 */
public class SearchCityPresenter {
    private static final String TAG = "SearchCityPresenter";
    private SearchCityActivity mSearchCityActivity;

    public SearchCityPresenter(SearchCityActivity searchCityActivity) {
        this.mSearchCityActivity = searchCityActivity;
    }

    /**
     * 获取热门城市信息
     */
    public void getHotCityListInfo() {
        Observable<Response> observable = HttpUtil.getGeoService().getHotCityInfo();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> mSearchCityActivity.handleHotCity(response));
    }

    /**
     * 获取城市列表信息
     *
     * @param location location
     */
    public void getCityListInfo(String location) {
        Observable<Response> observable = HttpUtil.getGeoService().getCityInfo(location);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> mSearchCityActivity.handSearchCity(response));
    }

}
