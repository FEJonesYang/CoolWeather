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

import com.example.kuou.db.City;
import com.example.kuou.db.County;
import com.example.kuou.db.Province;
import com.example.kuou.util.HttpUtil;
import com.example.kuou.util.Utility;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

    //省列表
    private List<Province> provinceList;
    //市列表
    private List<City> cityList;
    //县列表
    private List<County> countyList;
    //选中的省份
    private Province selectedProvince;
    //选中的城市
    private City selectedCity;
    //当前选中的级别
    private int currentLevel;

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
        assert getContext()!= null;
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
                if (currentLevel == LEVEL_PROVINCE) { //如果当前所在 省级 层次，则从provinceList获取数据
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) { //如果当前所在 市级 层次，则从 cityList 获取数据
                    selectedCity = cityList.get(position);
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY){//如果当前所在 县级 层次，则从 countyList 获取数据
                    //根据县级数据获取天气的 id
                    String weatherId = countyList.get(position).getWeatherId();
                    if (getActivity() instanceof MainActivity){ //如果当前 Activity 是 MainActivity
                        //跳转到 WeatherActivity ，并把 天气 id 传过去
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weather_id", weatherId);
                        startActivity(intent);
                        //销毁当前的 MainActivity
                        getActivity().finish();
                    }else if (getActivity() instanceof WeatherActivity){ //如果当前 Activity 是 WeatherActivity
                        WeatherActivity activity = (WeatherActivity) getActivity();
                        //关闭滑动抽屉
                        activity.drawerLayout.closeDrawers();
                        //刷新界面
                        activity.swipeRefreshLayout.setRefreshing(true);
                        //请求天气的数据
                        activity.requestWeather(weatherId);
                    }

                }
            }
        });

        // 为返回 bottom 设置点击事件
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY) { //如果当前处于 县级
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {   //如果当前处于 市级
                    queryProvince();
                }
            }
        });
        // 在 MainActivity 加载完以后调用这个方法，加载省级数据
        queryProvince();
    }


    /**
     * 查询选中省内的所有的省，优先从数据库开始查询，如果没有查询到再去服务器上查询
     */
    private void queryProvince() {
        //首先设置标题
        titleText.setText("中国");
        //处于省级列表，没有返回 bottom
        backButton.setVisibility(View.GONE);
        // 拿出所有的数据存放在 provinceList 中
        provinceList = LitePal.findAll(Province.class);
        if (provinceList.size() > 0) {
            //首先应该清除 datalist 中的数据，否则后面会出现异常
            datalist.clear();
            //迭代 provinceList 获取省级数据，并且把它添加到 当前 datalist 中
            for (Province province : provinceList) {
                datalist.add(province.getProvinceName());
            }
            // 数据发生变化，进行通知
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            // 如果数据库中没有数据，则到服务器中查询
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "province");
        }
    }


    /**
     * 查询选中省内的所有市，优先从数据库开始查询，如果没有查询到再去服务器上查询
     */

    private void queryCities() {
        //设置标题：省的名称
        titleText.setText(selectedProvince.getProvinceName());
        //让返回 bottom 可见
        backButton.setVisibility(View.VISIBLE);
        //根据被选中的城市进行查询
        cityList = LitePal.where("provinceId = ?", String.valueOf(selectedProvince.getId())).find(City.class);

        //如果查询到的数据不为0
        if (cityList.size() > 0) {
            //清除当前数据的中的元素
            datalist.clear();
            //遍历 cityList ，并添加到 datalist 中
            for (City city : cityList) {
                datalist.add(city.getCityName());
            }
            //通知数据发生变化
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else { //如果没有查到，就去服务器中查询
            int provinceCode = selectedProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(address, "city");
        }
    }


    /**
     * 查询选中城市的所有县，优先从数据库开始查询，如果没有查询到再去服务器上查询
     */
    private void queryCounties() {
        //标题应该为城市
        titleText.setText(selectedCity.getCityName());
        //返回 bottom 可见
        backButton.setVisibility(View.VISIBLE);
        // 根据城市 id 查询县级数据
        countyList = LitePal.where("cityId = ?", String.valueOf(selectedCity.getId())).find(County.class);

        //获取到的数据为 0
        if (countyList.size() > 0) {
            datalist.clear();
            for (County county : countyList) {
                datalist.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            //到服务器中去查询数据
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(address, "county");
        }
    }


    /**
     * 根据传入的地址和类型从服务器查询省市县的数据
     */
    private void queryFromServer(String address, final String type) {
        //在获取数据成功之前，首先应该显示对话框
        showProgressDialog();
        //发送数据请求
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //通过runOnUiThread回到主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //关闭对话框
                        closeProgressDialog();
                        //显示加载失败的提示
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String responseText = response.body().string();
                boolean result = false;

                // 根据传入的类型进行不同的操作
                if ("province".equals(type)) {
                    //解析省级数据，如果成功就会返回true
                    result = Utility.handleProvinceResponse(responseText);
                } else if ("city".equals(type)) {
                    //解析市级数据，如果成功就会返回true
                    result = Utility.handleCityResponse(responseText, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    //解析县级数据，如果成功就会返回true
                    result = Utility.handleCountyResponse(responseText, selectedCity.getId());
                }

                //如果数据解析成功
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvince();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });

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
