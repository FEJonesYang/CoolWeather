package com.jonesyong.module_detail.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jonesyong.library_common.model.DailyModel;
import com.jonesyong.library_common.utils.CommonUtil;
import com.jonesyong.module_detail.R;

import java.util.List;

/**
 * @Author JonesYang
 * @Date 2022-01-08
 * @Description
 */
public class SuggestionRecyclerAdapter extends RecyclerView.Adapter<SuggestionRecyclerAdapter.ViewHolder> {

    List<DailyModel> mDailyBeans;

    public SuggestionRecyclerAdapter(List<DailyModel> daily) {
        this.mDailyBeans = daily;
    }

    @NonNull
    @Override
    public SuggestionRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suggestion_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionRecyclerAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.refreshData(mDailyBeans.get(position));
    }

    @Override
    public int getItemCount() {
        if (mDailyBeans != null) {
            return mDailyBeans.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mSuggestionName;
        private final TextView mSuggestionLevel;
        private final TextView mSuggestionContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSuggestionName = itemView.findViewById(R.id.tv_suggestion_name);
            mSuggestionLevel = itemView.findViewById(R.id.tv_suggestion_level);
            mSuggestionContent = itemView.findViewById(R.id.tv_suggestion_text);
        }

        public void refreshData(DailyModel dailyBean) {
            if (dailyBean == null) {
                return;
            }
            if (CommonUtil.isStringValid(dailyBean.getName())) {
                mSuggestionName.setText(dailyBean.getName());
            }
            if (CommonUtil.isStringValid(dailyBean.getCategory())) {
                mSuggestionLevel.setText(dailyBean.getCategory());
            }
            if (CommonUtil.isStringValid(dailyBean.getText())) {
                mSuggestionContent.setText(dailyBean.getText());
            }
        }
    }
}
