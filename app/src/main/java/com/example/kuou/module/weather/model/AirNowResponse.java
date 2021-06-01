package com.example.kuou.module.weather.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author JonesYang
 * @Data 2021-04-13
 * @Function 当天空气质量数据实体
 */

public class AirNowResponse {

    /**
     * code : 200
     * updateTime : 2021-06-01T14:52+08:00
     * fxLink : http://hfx.link/2ax1
     * now : {"obsTime":"2021-06-01T14:43+08:00","temp":"28","feelsLike":"26","icon":"101","text":"多云","wind360":"135","windDir":"东南风","windScale":"3","windSpeed":"12","humidity":"23","precip":"0.0","pressure":"1004","vis":"30","cloud":"91","dew":"6"}
     * refer : {"sources":["Weather China"],"license":["no commercial use"]}
     */

    private String code;
    private String updateTime;
    private String fxLink;
    private NowBean now;
    private ReferBean refer;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getFxLink() {
        return fxLink;
    }

    public void setFxLink(String fxLink) {
        this.fxLink = fxLink;
    }

    public NowBean getNow() {
        return now;
    }

    public void setNow(NowBean now) {
        this.now = now;
    }

    public ReferBean getRefer() {
        return refer;
    }

    public void setRefer(ReferBean refer) {
        this.refer = refer;
    }

    public static class NowBean implements Serializable {
        /**
         * obsTime : 2021-06-01T14:43+08:00
         * temp : 28
         * feelsLike : 26
         * icon : 101
         * text : 多云
         * wind360 : 135
         * windDir : 东南风
         * windScale : 3
         * windSpeed : 12
         * humidity : 23
         * precip : 0.0
         * pressure : 1004
         * vis : 30
         * cloud : 91
         * dew : 6
         */

        private String obsTime;
        private String temp;
        private String feelsLike;
        private String icon;
        private String text;
        private String wind360;
        private String windDir;
        private String windScale;
        private String windSpeed;
        private String humidity;
        private String precip;
        private String pressure;
        private String vis;
        private String cloud;
        private String dew;

        public String getObsTime() {
            return obsTime;
        }

        public void setObsTime(String obsTime) {
            this.obsTime = obsTime;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getFeelsLike() {
            return feelsLike;
        }

        public void setFeelsLike(String feelsLike) {
            this.feelsLike = feelsLike;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getWind360() {
            return wind360;
        }

        public void setWind360(String wind360) {
            this.wind360 = wind360;
        }

        public String getWindDir() {
            return windDir;
        }

        public void setWindDir(String windDir) {
            this.windDir = windDir;
        }

        public String getWindScale() {
            return windScale;
        }

        public void setWindScale(String windScale) {
            this.windScale = windScale;
        }

        public String getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(String windSpeed) {
            this.windSpeed = windSpeed;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getPrecip() {
            return precip;
        }

        public void setPrecip(String precip) {
            this.precip = precip;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public String getCloud() {
            return cloud;
        }

        public void setCloud(String cloud) {
            this.cloud = cloud;
        }

        public String getDew() {
            return dew;
        }

        public void setDew(String dew) {
            this.dew = dew;
        }

        @Override
        public String toString() {
            return "NowBean{" +
                    "obsTime='" + obsTime + '\'' +
                    ", temp='" + temp + '\'' +
                    ", feelsLike='" + feelsLike + '\'' +
                    ", icon='" + icon + '\'' +
                    ", text='" + text + '\'' +
                    ", wind360='" + wind360 + '\'' +
                    ", windDir='" + windDir + '\'' +
                    ", windScale='" + windScale + '\'' +
                    ", windSpeed='" + windSpeed + '\'' +
                    ", humidity='" + humidity + '\'' +
                    ", precip='" + precip + '\'' +
                    ", pressure='" + pressure + '\'' +
                    ", vis='" + vis + '\'' +
                    ", cloud='" + cloud + '\'' +
                    ", dew='" + dew + '\'' +
                    '}';
        }
    }

    public static class ReferBean implements Serializable {
        private List<String> sources;
        private List<String> license;

        public List<String> getSources() {
            return sources;
        }

        public void setSources(List<String> sources) {
            this.sources = sources;
        }

        public List<String> getLicense() {
            return license;
        }

        public void setLicense(List<String> license) {
            this.license = license;
        }

        @Override
        public String toString() {
            return "ReferBean{" +
                    "sources=" + sources +
                    ", license=" + license +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AirNowResponse{" +
                "code='" + code + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", fxLink='" + fxLink + '\'' +
                ", now=" + now +
                ", refer=" + refer +
                '}';
    }
}



