package com.jonesyong.module_search;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jonesyong.library_common.base.Router;
import com.jonesyong.library_common.json.Utility;
import com.jonesyong.library_common.message.HotCityEventMessage;
import com.jonesyong.library_common.net.Api;
import com.jonesyong.library_common.net.HttpUtil;
import com.jonesyong.module_search.adapter.SearchCityRecycleViewAdapter;
import com.jonesyong.module_search.database.HistoryWordDatabaseManager;
import com.jonesyong.module_search.database.SearchHistoryDataBaseHelper;
import com.jonesyong.library_common.model.HotCityResponse;
import com.jonesyong.library_common.model.SearchCityBean;
import com.jonesyong.module_search.databinding.ActivitySearchCityBinding;
import com.jonesyong.module_search.ui.HotCityFlowLayout;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author JonesYang
 * @Data 2021-05-28
 * @Function 搜索城市的数据提供者
 */
public class SearchCityPresenter {
    private static final String TAG = "SearchCityPresenter";
    private SearchCityActivity mSearchCityActivity;

    // 热门城市布局
    public HotCityFlowLayout hotCityFlowLayout;
    public List<HotCityResponse.TopCityListDTO> listDTOS = new ArrayList<>();
    public RecyclerView recyclerViewCityList;
    public TextView hotCityShow;
    public ActivitySearchCityBinding mCityBinding;
    public RecyclerView mRecyclerView;
    public ScrollView mScrollView;
    public ImageView mIvDeleteInput;
    public EditText mEditText;
    public SearchCityRecycleViewAdapter mAdapter;


    // 数据库存储，历史词
    public SearchHistoryDataBaseHelper mDataBaseHelper;
    public HistoryWordDatabaseManager mDatabaseManager;
    List<String> mHistoryWordArray = new ArrayList<>();
    private LinearLayout mHistoryWordLayout;
    private TextView mTextViewShowHistory;
    private LinearLayout mErrorAndEmptyLayout;
    private Button mRetry;

    public SearchCityPresenter(SearchCityActivity searchCityActivity) {
        this.mSearchCityActivity = searchCityActivity;
    }

    // 查询城市
    public void queryLocation(String location) {
        HttpUtil.sendOkHttpRequest(Api.queryCity + location, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                SearchCityBean searchCityBean = gson.fromJson(response.body().string(), SearchCityBean.class);
                mISendSearchCityListener.PostSearchData(searchCityBean);
            }
        });
    }

    // 查询热门城市
    public void queryHotLocation() {
        HttpUtil.sendOkHttpRequest(Api.queryHotCity, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                HotCityResponse hotCityResponse = Utility.getGsonInstance().fromJson(response.body().string(), HotCityResponse.class);
                mISendSearchCityListener.postHotCityData(hotCityResponse);
            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void handleHotCity(HotCityResponse hotCityResponse) {
        // 如果热门城市的标签是 Gone 的，需要 Visible
        if (hotCityShow.getVisibility() == View.GONE) {
            hotCityShow.setVisibility(View.VISIBLE);
            mErrorAndEmptyLayout.setVisibility(View.GONE);
        }
        listDTOS = hotCityResponse.getTopCityList();

        for (int i = 0; i < listDTOS.size(); i++) {
            TextView childView = new TextView(mSearchCityActivity);
            // 设置名字
            childView.setText(hotCityResponse.getTopCityList().get(i).getName());
            // 背景
            childView.setBackground(mSearchCityActivity.getDrawable(R.drawable.shape_bg_hot_city_item));
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
                childView.setBackground(mSearchCityActivity.getDrawable(R.drawable.shape_bg_hot_city_item_select));
                HotCityEventMessage hotCityEventMessage = new HotCityEventMessage(hotCityResponse, index);
                EventBus.getDefault().post(hotCityEventMessage);
                ARouter.getInstance().build(Router.MODULE_HOME_WEATHER_ACTIVITY).navigation();
            });
            hotCityFlowLayout.addView(childView);
        }
    }

    public void initView() {
        mCityBinding = DataBindingUtil.setContentView(mSearchCityActivity, R.layout.activity_search_city);
        mRecyclerView = mCityBinding.containerCity;
        mScrollView = mCityBinding.scrollCity;
        // 返回按钮
        mCityBinding.includeSearch.btnBack.setOnClickListener((l) -> {
            ARouter.getInstance().build(Router.MODULE_HOME_WEATHER_ACTIVITY).navigation();
        });
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

        // 城市列表
        recyclerViewCityList = mCityBinding.containerCity;

        mErrorAndEmptyLayout = mSearchCityActivity.findViewById(R.id.ll_error_empty_container);
    }

    public void initEvent() {
        // 设置监听器
        setISendSearchCityListener(mSearchCityActivity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mSearchCityActivity));
        mAdapter = new SearchCityRecycleViewAdapter(mSearchCityActivity,mDatabaseManager);
        mRecyclerView.setAdapter(mAdapter);

        // 历史词相关的事件
        dealHistory();

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
                    recyclerViewCityList.setVisibility(View.GONE);

                } else {
                    // 历史记录
                    mHistoryWordLayout.setVisibility(View.GONE);
                    mTextViewShowHistory.setVisibility(View.GONE);
                    // 热门城市
                    hotCityShow.setVisibility(View.GONE);
                    hotCityFlowLayout.setVisibility(View.GONE);
                    recyclerViewCityList.setVisibility(View.VISIBLE);
                    //发起网络请求
                    queryLocation(s.toString());
                }
            }
        });

        // 热门城市网络请求数据
        queryHotLocation();
    }

    /**
     * 数据库的初始化,包括加载历史数据的视图
     */
    public void initDatabase() {
        mDataBaseHelper = new SearchHistoryDataBaseHelper(mSearchCityActivity);
        mDatabaseManager = HistoryWordDatabaseManager.createInstance();
        mDatabaseManager.openDataBase(mDataBaseHelper);
    }

    /**
     * 历史词的事件初始化，增删查改的事件 Action
     */
    private void dealHistory() {
        // 查询数据库
        queryDatabase();
    }

    private void queryDatabase() {
        // 查询的时候，如果存储数据的数组不为空，则进行 Clear 操作
        if (!this.mHistoryWordArray.isEmpty()) {
            this.mHistoryWordArray.clear();
        }
        Cursor cursor = mDatabaseManager.query();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String value = cursor.getString(cursor.getColumnIndex("history_key"));
            if (value != null   && !value.isEmpty()) {
                this.mHistoryWordArray.add(value);
            }
        }
        // 如果没有数据，不更新视图，返回
        if (mHistoryWordArray.isEmpty()) {
            mTextViewShowHistory.setVisibility(View.GONE);
            mHistoryWordLayout.setVisibility(View.GONE);
            return;
        }
        // 更新历史词的 View
        updateHistoryView();
    }

    /**
     * 更新数据库的视图
     */
    private void updateHistoryView() {
        // 每次在更新视图前需要移除之前的视图
        mHistoryWordLayout.removeAllViews();
        for (int i = 0; i < mHistoryWordArray.size(); i++) {
            if (mTextViewShowHistory.getVisibility() != View.VISIBLE) {
                mTextViewShowHistory.setVisibility(View.VISIBLE);
                mHistoryWordLayout.setVisibility(View.VISIBLE);
            }
            View view = LayoutInflater.from(mSearchCityActivity)
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
                queryDatabase();
            });
        }
    }

    public void showEmptyView() {

    }

    public void showErrorView() {
        mErrorAndEmptyLayout.setVisibility(View.VISIBLE);
        mRetry = mSearchCityActivity.findViewById(R.id.bt_retry);
        mRetry.setOnClickListener(v -> {
            if (!HttpUtil.isNetWorkConnection(mSearchCityActivity)) {
                Toast.makeText(mSearchCityActivity, "当前没有网络，请检查网络哦", Toast.LENGTH_SHORT).show();
                return;
            }
            queryHotLocation();
        });
    }


    // 接口实现数据传递到SearchCityActivity
    private ISendSearchCityListener mISendSearchCityListener;

    public void setISendSearchCityListener(ISendSearchCityListener ISendSearchCityListener) {
        mISendSearchCityListener = ISendSearchCityListener;
    }

    public interface ISendSearchCityListener {
        // 城市搜索数据
        void PostSearchData(SearchCityBean searchCityBean);

        // 热门城市数据
        void postHotCityData(HotCityResponse hotCityResponse);
    }

}
