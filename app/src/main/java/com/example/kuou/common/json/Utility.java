package com.example.kuou.common.json;

import android.text.TextUtils;
import android.util.Log;

import com.example.kuou.db.City;
import com.example.kuou.db.County;
import com.example.kuou.db.Province;
import com.example.kuou.gson.Weather;
import com.google.gson.Gson;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;

/**
 * @author JonesYang
 * @Data 2020-08-16
 * @Function 处理和解决服务器返回的JSON数据
 */


public class Utility {
    // 解析省级数据
    public static boolean handleProvinceResponse(String response) {
        //使用 TextUtils.isEmpty(response) 可以在 response 为空时，避免发生 空指针异常
        if (!TextUtils.isEmpty(response)) {
            try {
                // 所有的省级信息构成一个 JSONArray
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    //获取每一个省级的信息
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    //保存到数据库中
                    province.save();
                }
                //如果解析成功
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //没有解析成功
        return false;
    }

    // 解析市级数据
    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // 解析县级数据
    public static boolean handleCountyResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCityId(cityId);
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 返回的JSON解析成Weather实体类
     */
    @Deprecated
    public static Weather handleWeatherResponse(String response) {

        try {
            //把获取的数据解析成 JSON
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    // TODO 封装成一个数据解析的方法
    public Object handleWeatherResponse(Object object, Response response) {

        return null;
    }
}

