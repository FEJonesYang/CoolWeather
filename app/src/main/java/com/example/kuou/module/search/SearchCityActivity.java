package com.example.kuou.module.search;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.TextView;

import com.example.kuou.R;
import com.example.kuou.common.message.HotCityEventMessage;
import com.example.kuou.databinding.ActivitySearchCityBinding;
import com.example.kuou.module.search.adapter.SearchCityRecycleViewAdapter;
import com.example.kuou.module.search.model.HotCityResponse;
import com.example.kuou.module.search.model.SearchCityBean;
import com.example.kuou.module.search.ui.HotCityFlowLayout;

import org.greenrobot.eventbus.EventBus;

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
    private static final int POST_HOT_CITY_DATA = 2;
    // 热门城市布局
    private HotCityFlowLayout hotCityFlowLayout;
    private List<HotCityResponse.TopCityListDTO> listDTOS = new ArrayList<>();
    private RecyclerView recyclerViewCityList;
    private TextView hotCityShow;

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
            hotCityFlowLayout.setVisibility(View.VISIBLE);
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
                // 如果输入框的长度等于0，则显示热点城市的布局，否则显示城市列表布局
                if (s.length() == 0) {
                    hotCityFlowLayout.setVisibility(View.VISIBLE);
                    recyclerViewCityList.setVisibility(View.GONE);
                    hotCityShow.setVisibility(View.VISIBLE);
                } else {
                    hotCityShow.setVisibility(View.GONE);
                    hotCityFlowLayout.setVisibility(View.GONE);
                    recyclerViewCityList.setVisibility(View.VISIBLE);
                }
                //发起网络请求
                SearchCityPresenter.getInstance().queryLocation(s.toString());
            }
        });

        // 热门城市网络请求数据
        SearchCityPresenter.getInstance().queryHotLocation();

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

        // 输入框
        mEditText = mCityBinding.includeSearch.etInputCity;

        // 删除按钮
        mIvDeleteInput = mCityBinding.includeSearch.ivDeleteInput;

        // 热门城市
        hotCityFlowLayout = mCityBinding.hotCityContainer;
        hotCityShow = mCityBinding.tvShowHotCity;

        // 城市列表
        recyclerViewCityList = mCityBinding.containerCity;

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
        Log.d(TAG, hotCityResponse.toString());
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
    private void handleHotCity(HotCityResponse hotCityResponse) {
        this.listDTOS = hotCityResponse.getTopCityList();

        for (int i = 0; i < listDTOS.size(); i++) {
            TextView childView = new TextView(this);
            // 设置名字
            childView.setText(hotCityResponse.getTopCityList().get(i).getName());
            // 背景
            childView.setBackground(getDrawable(R.drawable.shape_bg_hot_city_item));
            childView.setPadding(20, 5, 20, 5);
            // 设置点击事件,
            int index = i;
            childView.setOnClickListener((l) -> {
                childView.setBackground(getDrawable(R.drawable.shape_bg_hot_city_item_select));
                EventBus.getDefault().post(new HotCityEventMessage(hotCityResponse, index));
                Intent intent = new Intent("com.example.kuou.module.weather.WeatherActivity");
                startActivity(intent);
            });
            hotCityFlowLayout.addView(childView);
        }


    }

    /**
     * 使用Handler的静态内部类进行线程切换
     */
    private static class SearchCityHandler extends Handler {

        private WeakReference<SearchCityActivity> mWeakReference;

        public SearchCityHandler(SearchCityActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                // 处理城市信息查询的
                case POST_SEARCH_CITY_DATA:
                    mWeakReference.get().mAdapter.setSearchCityData((SearchCityBean) msg.obj);
                    break;
                // 处理热门城市的数据
                case POST_HOT_CITY_DATA:
                    mWeakReference.get().handleHotCity((HotCityResponse) msg.obj);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}