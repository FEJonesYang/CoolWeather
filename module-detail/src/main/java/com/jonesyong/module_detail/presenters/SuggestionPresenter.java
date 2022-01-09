package com.jonesyong.module_detail.presenters;

import android.util.Log;

import com.jonesyong.library_common.base.BasePresenter;
import com.jonesyong.library_common.model.LifestyleResponse;
import com.jonesyong.library_common.net.HttpUtil;
import com.jonesyong.module_detail.adapter.SuggestionRecyclerAdapter;
import com.jonesyong.module_detail.fragments.SuggestionFragment;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
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
        Observable<LifestyleResponse> observable = HttpUtil.getDevService().getSuggestResult(location);
        observable.observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LifestyleResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull LifestyleResponse lifestyleResponse) {
                        if (lifestyleResponse.getDaily() == null || lifestyleResponse.getDaily().isEmpty()) {
                            mSuggestionFragment.showEmptyPage();
                        }
                        mSuggestionFragment.initData(lifestyleResponse);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        mIPageStatus.showErrorPage();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
