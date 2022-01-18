package com.jonesyong.library_common.model;

import java.io.Serializable;

/**
 * @Author JonesYang
 * @Date 2022-01-10
 * @Description 今日天气数据
 */

public class DailyModel implements Serializable {

    // 实时天气数据 bean
    private String fxDate;
    private String sunrise;
    private String sunset;
    private String moonrise;
    private String moonset;
    private String moonPhase;
    private String tempMax;
    private String tempMin;
    private String iconDay;
    private String textDay;
    private String iconNight;
    private String textNight;
    private String wind360Day;
    private String windDirDay;
    private String windScaleDay;
    private String windSpeedDay;
    private String wind360Night;
    private String windDirNight;
    private String windScaleNight;
    private String windSpeedNight;
    private String humidity;
    private String precip;
    private String pressure;
    private String vis;
    private String cloud;
    private String uvIndex;

    // 生活建议数据bean
    private String date;
    private String type;
    private String name;
    private String level;
    private String category;
    private String text;

    public String getFxDate() {
        return fxDate;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getMoonrise() {
        return moonrise;
    }

    public String getMoonset() {
        return moonset;
    }

    public String getMoonPhase() {
        return moonPhase;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getIconDay() {
        return iconDay;
    }

    public String getTextDay() {
        return textDay;
    }

    public String getIconNight() {
        return iconNight;
    }

    public String getTextNight() {
        return textNight;
    }

    public String getWind360Day() {
        return wind360Day;
    }

    public String getWindDirDay() {
        return windDirDay;
    }

    public String getWindScaleDay() {
        return windScaleDay;
    }

    public String getWindSpeedDay() {
        return windSpeedDay;
    }

    public String getWind360Night() {
        return wind360Night;
    }

    public String getWindDirNight() {
        return windDirNight;
    }

    public String getWindScaleNight() {
        return windScaleNight;
    }

    public String getWindSpeedNight() {
        return windSpeedNight;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPrecip() {
        return precip;
    }

    public String getPressure() {
        return pressure;
    }

    public String getVis() {
        return vis;
    }

    public String getCloud() {
        return cloud;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public String getCategory() {
        return category;
    }

    public String getText() {
        return text;
    }
}
