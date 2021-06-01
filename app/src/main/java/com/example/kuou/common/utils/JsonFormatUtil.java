package com.example.kuou.common.utils;


import org.json.JSONArray;

import org.json.JSONObject;

/**
 * @author JonesYang
 * @Data 2021-04-15
 * @Function json 数据格式化显示
 */
public class JsonFormatUtil {

    /**
     * json 数据格式化输出
     *
     * @param response
     * @return
     */
    public static String formatDataFromJson(String response) {
        try {
            if (response.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(response);
                return jsonObject.toString(4);
            } else if (response.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(response);
                return jsonArray.toString(4);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}


