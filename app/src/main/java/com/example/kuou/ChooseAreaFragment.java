package com.example.kuou;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.util.ArrayList;
import java.util.List;


/**
 * @author JonesYang
 * @Data 2020-08-16
 * @Function 遍历省市县数据的碎片
 */
public class ChooseAreaFragment extends Fragment {

    //级别信息，用来做判断
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> datalist = new ArrayList<>();


    /**
     * 加载Fragment 的布局
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //加载布局
        View view = inflater.inflate(R.layout.choose_area, container, false);
        //获取中间显示的标题
        titleText = (TextView) view.findViewById(R.id.title_text);
        //获取返回的 bottom
        backButton = (Button) view.findViewById(R.id.back_button);
        //获取 ListView 控件
        listView = (ListView) view.findViewById(R.id.list_view);
        assert getContext() != null;
        // 定义 ListView 的适配器，这里采用系统定义好的适配器
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, datalist);
        //添加适配器
        listView.setAdapter(adapter);
        //返回获取的 View
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //ListView 设置点击事件，获取到点击的 item 的 position
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        // 为返回 bottom 设置点击事件
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // 在 MainActivity 加载完以后调用这个方法，加载省级数据
        queryProvince();
    }


    /**
     * 查询选中省内的所有的省，优先从数据库开始查询，如果没有查询到再去服务器上查询
     */
    private void queryProvince() {
    }


    /**
     * 查询选中省内的所有市，优先从数据库开始查询，如果没有查询到再去服务器上查询
     */

    private void queryCities() {

    }


    /**
     * 查询选中城市的所有县，优先从数据库开始查询，如果没有查询到再去服务器上查询
     */
    private void queryCounties() {

    }


    /**
     * 显示对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }


    /**
     * 关闭对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
