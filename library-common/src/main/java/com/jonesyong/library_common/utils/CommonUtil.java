package com.jonesyong.library_common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * @Author JonesYang
 * @Date 2022-01-10
 * @Description 一般工具类，例如判断字符串是否相同
 */
public class CommonUtil {


    /**
     * @param s 判断字符
     * @return true 不为null，且长度有效
     */
    public static boolean isStringValid(String s) {
        if (s == null) {
            return false;
        }
        return s.length() != 0;
    }

    /**
     * @return true 两个字符串内容相同
     */
    public static boolean isStringEquals(String s1, String s2) {
        return s1.equals(s2);
    }


    /**
     * 从缓存获取 location
     *
     * @param context 上下文
     * @return
     */
    public static String getLocationIdFromCache(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        return sharedPreferences.getString("locationId", null);
    }

    public static String getCityNameFromCache(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        return sharedPreferences.getString("cityName", null);
    }

    public static void putLocationToCache(Context context, String locationId, String cityName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sharedPreferences.edit().putString("locationId", locationId).putString("cityName", cityName).apply();
        }
    }

}
