package com.jonesyong.module_detail.presenters;


import com.jonesyong.library_common.base.BasePresenter;
import com.jonesyong.library_common.model.Response;
import com.jonesyong.library_common.net.HttpUtil;
import com.jonesyong.module_detail.fragments.SuggestionFragment;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @Author JonesYang
 * @Date 2022-01-09
 * @Description 处理生活建议的
 */
public class SuggestionPresenter extends BasePresenter {

    private SuggestionFragment mSuggestionFragment;

    public SuggestionPresenter(SuggestionFragment suggestionFragment) {
        mSuggestionFragment = suggestionFragment;
    }

    /**
     * 获取生活建议的网络数据
     *
     * @param location
     */
    public void getSuggestionData(String location) {
        Observable<Response> observable = HttpUtil.getDevService().getSuggestResult(location);
        observable.observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    mSuggestionFragment.initData(response);
                });
    }
}
