package com.jonesyong.module_detail;

import android.util.Log;

import org.junit.Test;

import com.jonesyong.library_common.model.LifestyleResponse;
import com.jonesyong.library_common.net.HttpUtil;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public static final String TAG = "ExampleUnitTest";

    /**
     * 生活建议单元测试
     *
     * @throws
     */
    @Test
    public void suggestionIsCorrect() {
        Observable<LifestyleResponse> observable = HttpUtil.buildService("https://devapi.qweather.com/").getSuggestResult("101010100");
        observable.observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LifestyleResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull LifestyleResponse lifestyleResponse) {
                        Log.d(TAG,"Succeed");
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}