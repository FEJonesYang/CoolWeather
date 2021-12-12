package com.example.kuou.base;

import java.lang.ref.PhantomReference;
import java.lang.ref.WeakReference;

/**
 * @author JonesYang
 * @Data 2021-04-10
 * @Function 存储一些静态常量
 */
public class Constants {

    // 和风天气的 key
    public static final String HWeatherKey = "&key=575a4dd45c9f46ca833a259f953c76b3";

    // 高德地图接入的key
    public static final String MapKey = "817296f9fdbec904aa9a5ad74ea8bfe1";

    // 中国省级城市列表
    public static final String[] provinceList = {
            "北京市",
            "天津市",
            "河北省",
            "山西省",
            "内蒙古自治区",
            "辽宁省",
            "吉林省",
            "黑龙江省",
            "上海市",
            "江苏省",
            "浙江省",
            "安徽省",
            "福建省",
            "江西省",
            "山东省",
            "河南省",
            "湖北省",
            "湖南省",
            "广东省",
            "广西壮族自治区",
            "海南省",
            "重庆市",
            "四川省",
            "贵州省",
            "云南省",
            "西藏自治区",
            "陕西省",
            "甘肃省",
            "青海省",
            "宁夏回族自治区",
            "新疆维吾尔自治区"
    };

    /**
     * 线程切换相关的 what 标志
     */
    public static final int POST_BACKGROUND_IMAGE = 1;
    public static final int POST_NOW_WEATHER_DATA = 2;
    public static final int POST_FORECAST_WEATHER_DATA = 3;
    public static final int POST_AIR_CONDITION_DATA = 4;
    public static final int POST_LIFE_SUGGESTION = 5;

    // 是否 DEBUG
    public static final boolean isDebug = true;

}
