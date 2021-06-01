package com.example.kuou.common.net;

/**
 * @author JonesYang
 * @Data 2021-04-11
 * @Function 来自服务器的 API
 */
public class Api {

    // baseUrl
    public static final String baseUrl = "https://geoapi.qweather.com";


    // 城市信息查询 https://geoapi.qweather.com/v2/city/lookup?location=beij&key=575a4dd45c9f46ca833a259f953c76b3
    public static final String queryCity = baseUrl + "/v2/city/lookup?location=";

    // 热门城市查询 中国 top10 https://geoapi.qweather.com/v2/city/top?number=10&range=cn&key=575a4dd45c9f46ca833a259f953c76b3
    public static final String queryHotCity = baseUrl + "/v2/city/top?number=10&range=cn";

    // 某一地区景点的天气数据
    public static final String queryPOIInfo = baseUrl + "/v2/poi/lookup?type=scenic&location=";

    // 实时天气数据查询 https://devapi.qweather.com/v7/weather/now?location=101010100&key=575a4dd45c9f46ca833a259f953c76b3
    public static final String weatherNow = baseUrl + "/v7/weather/now?location=";

    // 逐天天气数据--3天 https://devapi.qweather.com/v7/weather/3d?location=101010100&key=575a4dd45c9f46ca833a259f953c76b3
    public static final String forecast3Day = baseUrl + "/v7/weather/3d?location=";

    // 逐天天气数据--7天 https://devapi.qweather.com/v7/weather/7d?location=101010100&key=575a4dd45c9f46ca833a259f953c76b3
    public static final String forecast7Day = baseUrl + "/v7/weather/7d?location=";

    // 逐天天气数据--15天 https://devapi.qweather.com/v7/weather/15d?location=101010100&key=575a4dd45c9f46ca833a259f953c76b3
    public static final String forecast15Day = baseUrl + "/v7/weather/15d?location=";

    // 天气生活指数--type 等于0时，请求所有的生活质量指数 https://devapi.qweather.com/v7/indices/1d?type=0&location=101010100&key=575a4dd45c9f46ca833a259f953c76b3
    public static final String lifeCondition = baseUrl + "/v7/indices/1d?type=0&location=";

    // 实时空气质量 https://devapi.qweather.com/v7/air/now?location=101010100&key=575a4dd45c9f46ca833a259f953c76b3
    public static final String airCondition = baseUrl + "/v7/air/now?location=";

    // 天气预警信息  https://devapi.qweather.com/v7/warning/now?location=101060101&key=575a4dd45c9f46ca833a259f953c76b3
    public static final String weatherWarn = baseUrl + "/v7/warning/now?location=";

    // 天气预警信息城市列表的查询  https://devapi.qweather.com/v7/warning/list?range=cn&key=575a4dd45c9f46ca833a259f953c76b3
    public static final String warnCityList = baseUrl + "/v7/warning/list?range=cn";

    // 历史天气数据查询 https://dev.qweather.com/docs/api/historical/historical-weather/
    public static final String historyWeather = baseUrl + "/v7/historical/weather?location=";

    // 历史空气质量数据查询 https://dev.qweather.com/docs/api/historical/historical-air/
    public static final String historyAirCondition = baseUrl + "/v7/historical/air?location=";

    // 背景图片的加载 https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN
    public static final String getBackgroundImageUrl = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN";

}
