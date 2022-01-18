package com.jonesyong.library_common.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Author JonesYang
 * @Date 2022-01-10
 * @Description 基础的 model 类
 */
public class Response implements Serializable {

    private String code; // 返回码

    private String updateTime; // 今日天气更新时间

    private String fxLink; // url 下发的是一个网页链接

    private NowModel now; // 实时天气查询

    private List<LocationModel> location; // 当前城市信息，包括城市名称以及location

    private List<HotCityListModel> topCityList; // 热门城市信息

    private List<DailyModel> daily;  // 实时天气数据

    private List<StationModel> station;

    private List<BackgroundModel> images; // 背景图片信息

    private List<WarnInfoModel> warning; // 警告的城市信息

    private List<WarnCityListModel> warningLocList; // 警告城市列表

    public List<BackgroundModel> getImages() {
        return images;
    }

    public NowModel getNow() {
        return now;
    }

    public List<LocationModel> getLocation() {
        return location;
    }

    public List<HotCityListModel> getTopCityList() {
        return topCityList;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public List<DailyModel> getDaily() {
        return daily;
    }

    public List<StationModel> getStation() {
        return station;
    }

    public List<WarnCityListModel> getWarningLocList() {
        return warningLocList;
    }

    public List<WarnInfoModel> getWarning() {
        return warning;
    }
}
