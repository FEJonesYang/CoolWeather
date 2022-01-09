package com.jonesyong.library_common.net;

/**
 * @author JonesYang
 * @Data 2021-04-11
 * @Function 来自服务器的 API
 */
public class Api {

    // baseUrl
    public static final String geoBaseUrl = "https://geoapi.qweather.com";

    public static final String devBaseUrl = "https://devapi.qweather.com";


    // 城市信息查询 https://geoapi.qweather.com/v2/city/lookup?location=beij&key=575a4dd45c9f46ca833a259f953c76b3
    public static final String queryCity = geoBaseUrl + "/v2/city/lookup?number=20&location=";

    // 热门城市查询 中国 top10 https://geoapi.qweather.com/v2/city/top?number=10&range=cn&key=575a4dd45c9f46ca833a259f953c76b3
    public static final String queryHotCity = geoBaseUrl + "/v2/city/top?number=10&range=cn";

    // 实时天气数据查询 https://devapi.qweather.com/v7/weather/now?location=101010100&key=575a4dd45c9f46ca833a259f953c76b3
    public static final String weatherNow = devBaseUrl + "/v7/weather/now?location=";

    // 逐天天气数据--3天 https://devapi.qweather.com/v7/weather/3d?location=101010100&key=575a4dd45c9f46ca833a259f953c76b3
    public static final String forecast3Day = devBaseUrl + "/v7/weather/3d?location=";

    // 天气生活指数--type 等于0时，请求所有的生活质量指数 https://devapi.qweather.com/v7/indices/1d?type=0&location=101010100&key=575a4dd45c9f46ca833a259f953c76b3
    @Deprecated
    public static final String lifeCondition = devBaseUrl + "/v7/indices/1d?type=0&location=";

    // 实时空气质量 https://devapi.qweather.com/v7/air/now?location=101010100&key=575a4dd45c9f46ca833a259f953c76b3
    public static final String airCondition = devBaseUrl + "/v7/air/now?location=";

    // 天气预警信息  https://devapi.qweather.com/v7/warning/now?location=101060101&key=575a4dd45c9f46ca833a259f953c76b3
    public static final String weatherWarn = devBaseUrl + "/v7/warning/now?location=";

    // 天气预警信息城市列表的查询  https://devapi.qweather.com/v7/warning/list?range=cn&key=575a4dd45c9f46ca833a259f953c76b3
    public static final String warnCityList = devBaseUrl + "/v7/warning/list?range=cn";

    // 背景图片的加载 https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN
    public static final String getBackgroundImageUrl = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN";

}
