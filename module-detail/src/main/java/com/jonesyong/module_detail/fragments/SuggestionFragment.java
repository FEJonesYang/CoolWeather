package com.jonesyong.module_detail.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jonesyong.library_common.base.BaseFragment;
import com.jonesyong.library_common.model.Response;
import com.jonesyong.module_detail.R;
import com.jonesyong.module_detail.adapter.SuggestionRecyclerAdapter;
import com.jonesyong.module_detail.presenters.SuggestionPresenter;


/**
 * @Author JonesYang
 * @Date 2022-01-07
 * @Description
 */
public class SuggestionFragment extends BaseFragment {

    private static final String TAG = "SuggestionFragment";

    private SuggestionPresenter mSuggestionPresenter = new SuggestionPresenter(this);
    private String location;
    private String cityName;
    private RecyclerView mSuggestionRecyclerView;
    private SuggestionRecyclerAdapter mAdapter;
    private TextView mCityNameView;

    public SuggestionFragment(String location, String cityName) {
        this.location = location;
        this.cityName = cityName;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEvent();
    }

    private void initEvent() {
        mSuggestionPresenter.getSuggestionData(location);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_suggestion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSuggestionRecyclerView = view.findViewById(R.id.rv_suggestion_container);
        mCityNameView = view.findViewById(R.id.title_city);
        mCityNameView.setText(cityName);
    }

    public void initData(Response response) {
        mSuggestionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SuggestionRecyclerAdapter(response.getDaily());
        mSuggestionRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showErrorPage() {

    }

    @Override
    public void showEmptyPage() {

    }

}