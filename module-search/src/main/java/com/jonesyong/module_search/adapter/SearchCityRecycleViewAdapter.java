package com.jonesyong.module_search.adapter;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jonesyong.library_common.base.Router;
import com.jonesyong.library_common.message.SearchCityEvent;
import com.jonesyong.library_common.model.LocationModel;
import com.jonesyong.library_common.model.Response;
import com.jonesyong.module_search.R;
import com.jonesyong.module_search.database.HistoryWordDatabaseManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JonesYang
 * @Data 2021-05-28
 * @Function 搜索界面 RecyclerView 对应的适配器
 */
public class SearchCityRecycleViewAdapter extends RecyclerView.Adapter<SearchCityRecycleViewAdapter.InnerViewHolder> {

    private static final String TAG = "SearchCityRecycleViewAdapter";
    List<LocationModel> mCityList = new ArrayList<>();
    Context mContext;
    private final HistoryWordDatabaseManager mDatabaseManager;

    public SearchCityRecycleViewAdapter(Context context, HistoryWordDatabaseManager databaseManager) {
        this.mDatabaseManager = databaseManager;
        this.mContext = context;
    }

    /**
     * 通过 LayoutInflater 获取布局视图
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public SearchCityRecycleViewAdapter.InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_search, parent, false);
        return new InnerViewHolder(view);
    }

    /**
     * @param holder   在onCreateViewHolder中返回视图对象
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull SearchCityRecycleViewAdapter.InnerViewHolder holder, int position) {
        holder.itemView.setTag(position);
        // 单个item的点击事件
        holder.itemView.setOnClickListener((l) -> {
            LocationModel location = mCityList.get(position);
            // 如果数据库中不存在相同的 key ，则进行插入操作
            if (!mDatabaseManager.isExitKey(location.getName())) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("history_key", location.getName());
                contentValues.put("history_value", location.getName());
                mDatabaseManager.insert(contentValues);
            }
            EventBus.getDefault().post(new SearchCityEvent(location));
            ARouter.getInstance().build(Router.MODULE_HOME_WEATHER_ACTIVITY).navigation();
        });
        holder.reFreshData(mCityList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCityList.size();
    }

    @SuppressLint("LongLogTag")
    public void setSearchCityData(Response response) {
        if (response != null && response.getLocation() != null) {
            Log.d(TAG, response.toString());
            if (mCityList.size() > 0) {
                mCityList.clear();
            }
            mCityList.addAll(response.getLocation());
            notifyDataSetChanged();
        }
    }

    public static class InnerViewHolder extends RecyclerView.ViewHolder {

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void reFreshData(LocationModel locationBean) {
            TextView textView = itemView.findViewById(R.id.tv_cityName);
            textView.setText(locationBean.getName());
        }
    }

}
