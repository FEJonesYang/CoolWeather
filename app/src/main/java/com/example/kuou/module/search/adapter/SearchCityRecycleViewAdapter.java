package com.example.kuou.module.search.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kuou.R;
import com.example.kuou.module.search.model.SearchCityBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JonesYang
 * @Data 2021-05-28
 * @Function 搜索界面 RecyclerView 对应的适配器
 */
public class SearchCityRecycleViewAdapter extends RecyclerView.Adapter<SearchCityRecycleViewAdapter.InnerViewHolder> {

    private static final String TAG = "SearchCityRecycleViewAdapter";
    List<SearchCityBean.LocationBean> mCityList = new ArrayList<>();
    Context mContext;

    public SearchCityRecycleViewAdapter(Context context) {
        mContext = context;
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
            mISendSearchCityDataToWeatherActivityListener.postSearchCityData(mCityList.get(position));
        });
        holder.reFreshData(mCityList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCityList.size();
    }

    @SuppressLint("LongLogTag")
    public void setSearchCityData(SearchCityBean searchCityBean) {
        if (searchCityBean != null && searchCityBean.getLocation() != null) {
            Log.d(TAG, searchCityBean.toString());
            if (mCityList.size() > 0) {
                mCityList.clear();
            }
            mCityList.addAll(searchCityBean.getLocation());
            notifyDataSetChanged();
        }
    }

    public class InnerViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void reFreshData(SearchCityBean.LocationBean locationBean) {
            mTextView = itemView.findViewById(R.id.tv_cityName);
            mTextView.setText(locationBean.getName());
        }
    }

    // 接口回调，数据传递到 WeatherActivity，点击单个item时需要
    private static ISendSearchCityDataToWeatherActivityListener mISendSearchCityDataToWeatherActivityListener;

    public static void setISendSearchCityDataToWeatherActivityListener(ISendSearchCityDataToWeatherActivityListener ISendSearchCityDataToWeatherActivityListener) {
        mISendSearchCityDataToWeatherActivityListener = ISendSearchCityDataToWeatherActivityListener;
    }

    public interface ISendSearchCityDataToWeatherActivityListener {
        void postSearchCityData(SearchCityBean.LocationBean locationBean);
    }
}
