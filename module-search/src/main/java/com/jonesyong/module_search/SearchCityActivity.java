package com.jonesyong.module_search;


import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jonesyong.library_common.base.BaseActivity;
import com.jonesyong.library_common.base.Router;
import com.jonesyong.library_common.message.HotCityEventMessage;
import com.jonesyong.library_common.model.HotCityListModel;
import com.jonesyong.library_common.model.Response;
import com.jonesyong.module_search.adapter.SearchCityRecycleViewAdapter;
import com.jonesyong.module_search.database.HistoryWordDatabaseManager;
import com.jonesyong.module_search.database.SearchHistoryDataBaseHelper;
import com.jonesyong.module_search.databinding.ActivitySearchCityBinding;
import com.jonesyong.module_search.ui.HotCityFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

@Route(path = Router.MODULE_SEARCH_SEARCH_ACTIVITY)
public class SearchCityActivity extends BaseActivity {

    private static final String TAG = "SearchCityActivity";
    private SearchCityPresenter mSearchCityPresenter;
    private ActivitySearchCityBinding mCityBinding;


    private RecyclerView mRecyclerView;
    private ScrollView mScrollView;
    private ImageView mIvDeleteInput;
    private EditText mEditText;
    private LinearLayout mHistoryWordLayout;
    private TextView mTextViewShowHistory;
    // 热门城市布局
    private HotCityFlowLayout hotCityFlowLayout;
    private TextView hotCityShow;

    // 数据库存储，历史词
    private SearchHistoryDataBaseHelper mDataBaseHelper;
    private HistoryWordDatabaseManager mDatabaseManager;
    List<String> mHistoryWordArray = new ArrayList<>();
    private SearchCityRecycleViewAdapter mAdapter;

    private List<HotCityListModel> hotCityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCityBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_city);
        mSearchCityPresenter = new SearchCityPresenter(this);
        initView();
        initDatabase();
        initEvent();
    }

    private void initView() {
        // 城市列表
        mRecyclerView = mCityBinding.containerCity;
        mScrollView = mCityBinding.scrollCity;
        // 输入框
        mEditText = mCityBinding.includeSearch.etInputCity;
        // 删除按钮
        mIvDeleteInput = mCityBinding.includeSearch.ivDeleteInput;
        // 历史词
        mHistoryWordLayout = mCityBinding.searchHistoryView;
        mTextViewShowHistory = mCityBinding.tvShowHistory;
        // 热门城市
        hotCityFlowLayout = mCityBinding.hotCityContainer;
        hotCityShow = mCityBinding.tvShowHotCity;
    }

    private void initEvent() {
        mSearchCityPresenter.getHotCityListInfo();
        // 返回按钮
        mCityBinding.includeSearch.btnBack.setOnClickListener((l) -> {
            ARouter.getInstance().build(Router.MODULE_HOME_WEATHER_ACTIVITY).navigation();
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchCityRecycleViewAdapter(this, mDatabaseManager);
        mRecyclerView.setAdapter(mAdapter);

        // 删除编辑框的内容以及搜索的点击事件
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
                if (s.toString().trim().isEmpty()) {
                    // 历史记录
                    mHistoryWordLayout.setVisibility(View.VISIBLE);
                    mTextViewShowHistory.setVisibility(View.VISIBLE);
                    // 热门城市
                    hotCityFlowLayout.setVisibility(View.VISIBLE);
                    hotCityShow.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);

                } else {
                    // 历史记录
                    mHistoryWordLayout.setVisibility(View.GONE);
                    mTextViewShowHistory.setVisibility(View.GONE);
                    // 热门城市
                    hotCityShow.setVisibility(View.GONE);
                    hotCityFlowLayout.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    //发起网络请求
                    mSearchCityPresenter.getCityListInfo(s.toString());
                }
            }
        });
        // 历史词相关的事件
        new Thread(this::queryDatabase).start();
    }

    /**
     * 历史词的事件初始化，增删查改的事件 Action
     */
    private void queryDatabase() {
        // 查询的时候，如果存储数据的数组不为空，则进行 Clear 操作
        if (!this.mHistoryWordArray.isEmpty()) {
            this.mHistoryWordArray.clear();
        }
        Cursor cursor = mDatabaseManager.query();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String value = cursor.getString(cursor.getColumnIndex("history_key"));
                if (value != null && !value.isEmpty()) {
                    this.mHistoryWordArray.add(value);
                }
            }
        }
        // 更新历史词的 View
        runOnUiThread(this::updateHistoryView);
    }

    /**
     * 更新数据库的视图
     */
    private void updateHistoryView() {
        if (mHistoryWordArray.isEmpty()) {
            mTextViewShowHistory.setVisibility(View.GONE);
            mHistoryWordLayout.setVisibility(View.GONE);
            return;
        }
        // 每次在更新视图前需要移除之前的视图
        mHistoryWordLayout.removeAllViews();
        for (int i = 0; i < mHistoryWordArray.size(); i++) {
            if (mTextViewShowHistory.getVisibility() != View.VISIBLE) {
                mTextViewShowHistory.setVisibility(View.VISIBLE);
                mHistoryWordLayout.setVisibility(View.VISIBLE);
            }
            View view = LayoutInflater.from(this)
                    .inflate(R.layout.item_search_history, mHistoryWordLayout, false);
            TextView contentView = view.findViewById(R.id.tv_search_history_item_content);
            ImageView deleteView = view.findViewById(R.id.im_search_history_item_delete);
            contentView.setText(mHistoryWordArray.get(i));
            mHistoryWordLayout.addView(view);
            // 点击删除按钮，触发删除事件
            deleteView.setOnClickListener(v -> {
                mHistoryWordLayout.removeView(view);
                // 删除的目标需要加 引号
                mDatabaseManager.delete("history_key = " + "'" + contentView.getText().toString().trim() + "'", null);
                // 需要调用这里判断是否还存在数据，不存在的话需要吧对应的 View 置 GONE
                mHistoryWordLayout.requestLayout();
            });
        }
    }

    /**
     * 数据库的初始化,包括加载历史数据的视图
     */
    public void initDatabase() {
        mDataBaseHelper = new SearchHistoryDataBaseHelper(this);
        mDatabaseManager = HistoryWordDatabaseManager.createInstance();
        mDatabaseManager.openDataBase(mDataBaseHelper);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void handleHotCity(Response response) {
        // 如果热门城市的标签是 Gone 的，需要 Visible
        if (hotCityShow.getVisibility() == View.GONE) {
            hotCityShow.setVisibility(View.VISIBLE);
        }
        hotCityList = response.getTopCityList();

        for (int i = 0; i < hotCityList.size(); i++) {
            TextView childView = new TextView(this);
            // 设置名字
            childView.setText(hotCityList.get(i).getName());
            // 背景
            childView.setBackground(getDrawable(R.drawable.shape_bg_hot_city_item));
            childView.setPadding(20, 5, 20, 5);
            // 设置点击事件,
            int index = i;
            childView.setOnClickListener((l) -> {
                if (!mDatabaseManager.isExitKey(childView.getText().toString())) {
                    ContentValues values = new ContentValues();
                    values.put("history_key", childView.getText().toString());
                    values.put("history_value", childView.getText().toString());
                    mDatabaseManager.insert(values);
                }
                childView.setBackground(getDrawable(R.drawable.shape_bg_hot_city_item_select));
                EventBus.getDefault().post(new HotCityEventMessage(hotCityList.get(index)));
                ARouter.getInstance().build(Router.MODULE_HOME_WEATHER_ACTIVITY).navigation();
            });
            hotCityFlowLayout.addView(childView);
        }
    }

    public void handSearchCity(Response response) {
        mAdapter.setSearchCityData(response);
    }
}