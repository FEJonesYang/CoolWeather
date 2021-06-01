package com.example.kuou.module.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.adapters.SearchViewBindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.kuou.R;
import com.example.kuou.common.net.Api;
import com.example.kuou.common.net.HttpUtil;
import com.example.kuou.databinding.ActivitySearchCityBinding;
import com.example.kuou.module.search.adapter.SearchCityRecycleViewAdapter;
import com.example.kuou.module.search.model.SearchCityBean;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class SearchCityActivity extends AppCompatActivity implements SearchCityPresenter.ISendSearchCityListener {

    private static final String TAG = "SearchCityActivity";
    private ActivitySearchCityBinding mCityBinding;
    private RecyclerView mRecyclerView;
    private ScrollView mScrollView;
    private SearchCityRecycleViewAdapter mAdapter;
    private ImageView mIvDeleteInput;
    private EditText mEditText;
    private SearchCityHandler mSearchCityHandler;

    // 处理城市搜索数据时，进行线程切换的标志
    public static final int POST_SEARCH_CITY_DATA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        super.onCreate(savedInstanceState);
        mCityBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_city);

        mSearchCityHandler = new SearchCityHandler(this);

        initView();
        initEvent();
    }

    /**
     * 初始化页面数据
     */
    private void initEvent() {
        // 设置监听器
        SearchCityPresenter.getInstance().setISendSearchCityListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchCityRecycleViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        // 删除编辑框的内容
        mIvDeleteInput.setOnClickListener((l) -> {
            mEditText.getText().clear();
        });

        // 文本框事件监听
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //发起网络请求
                SearchCityPresenter.getInstance().queryLocation(s.toString());
            }
        });
    }

    /**
     * 初始化页面的视图
     */
    private void initView() {
        mRecyclerView = mCityBinding.containerCity;
        mScrollView = mCityBinding.scrollCity;
        // 返回按钮
        mCityBinding.includeSearch.btnBack.setOnClickListener((l) -> {
            Intent intent = new Intent("com.example.kuou.module.weather.WeatherActivity");
            startActivity(intent);
        });

        mEditText = mCityBinding.includeSearch.etInputCity;

        mIvDeleteInput = mCityBinding.includeSearch.ivDeleteInput;

    }

    /**
     * 数据回调
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
     * 使用Handler的静态内部类进行线程切换
     */
    private static class SearchCityHandler extends Handler {

        private WeakReference<SearchCityActivity> mWeakReference;

        public SearchCityHandler(SearchCityActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                // 处理城市信息查询的
                case 1:
                    mWeakReference.get().mAdapter.setSearchCityData((SearchCityBean) msg.obj);
                    break;
                // TODO 处理热门城市搜索
            }
        }
    }

}