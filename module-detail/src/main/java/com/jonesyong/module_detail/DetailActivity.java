package com.jonesyong.module_detail;


import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jonesyong.library_common.base.BaseActivity;
import com.jonesyong.library_common.base.Router;
import com.jonesyong.module_detail.fragments.SuggestionFragment;

/**
 * @Author JonesYang
 * @Date 2022-01-04
 * @Description 详情页的 Activity，页面交给 Fragment 处理，这里只是一个空壳，承载了多个 Fragment
 */
@Route(path = Router.MODULE_DETAIL_DETAIL_ACTIVITY)
public class DetailActivity extends BaseActivity {

    private FrameLayout mFragmentContainer;

    @Autowired
    String location;
    @Autowired
    String cityName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        initEvent();
    }

    private void initView() {
        mFragmentContainer = findViewById(R.id.fl_fragment_container);
    }

    private void initEvent() {
        // 开启生活建议的 Fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fl_fragment_container,
                new SuggestionFragment(location, cityName)).commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
