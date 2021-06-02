package com.example.kuou.module.weather.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author JonesYang
 * @Data 2021-04-13
 * @Function 当天空气质量数据实体
 */

public class AirNowConditionResponse {

    /**
     * code : 200
     * updateTime : 2021-06-02T16:42+08:00
     * fxLink : http://hfx.link/2ax4
     * now : {"pubTime":"2021-06-02T16:00+08:00","aqi":"38","level":"1","category":"优","primary":"NA","pm10":"38","pm2p5":"11","no2":"9","so2":"2","co":"0.3","o3":"110"}
     * station : [{"pubTime":"2021-06-02T16:00+08:00","name":"丰台小屯","id":"CNA3696","aqi":"32","level":"1","category":"优","primary":"NA","pm10":"26","pm2p5":"9","no2":"11","so2":"1","co":"0.3","o3":"100"},{"pubTime":"2021-06-02T16:00+08:00","name":"怀柔新城","id":"CNA3695","aqi":"44","level":"1","category":"优","primary":"NA","pm10":"44","pm2p5":"26","no2":"9","so2":"3","co":"0.8","o3":"140"},{"pubTime":"2021-06-02T16:00+08:00","name":"延庆石河营","id":"CNA3694","aqi":"37","level":"1","category":"优","primary":"NA","pm10":"37","pm2p5":"4","no2":"6","so2":"1","co":"0.2","o3":"102"},{"pubTime":"2021-06-02T16:00+08:00","name":"房山燕山","id":"CNA3674","aqi":"63","level":"2","category":"良","primary":"PM10","pm10":"76","pm2p5":"30","no2":"21","so2":"1","co":"0.7","o3":"162"},{"pubTime":"2021-06-02T16:00+08:00","name":"丰台云岗","id":"CNA3672","aqi":"44","level":"1","category":"优","primary":"NA","pm10":"44","pm2p5":"18","no2":"14","so2":"2","co":"0.5","o3":"124"},{"pubTime":"2021-06-02T16:00+08:00","name":"门头沟三家店","id":"CNA3671","aqi":"38","level":"1","category":"优","primary":"NA","pm10":"38","pm2p5":"4","no2":"9","so2":"1","co":"0.2","o3":"114"},{"pubTime":"2021-06-02T16:00+08:00","name":"平谷新城","id":"CNA3417","aqi":"35","level":"1","category":"优","primary":"NA","pm10":"16","pm2p5":"13","no2":"6","so2":"1","co":"0.4","o3":"112"},{"pubTime":"2021-06-02T16:00+08:00","name":"延庆夏都","id":"CNA3281","aqi":"45","level":"1","category":"优","primary":"NA","pm10":"45","pm2p5":"4","no2":"11","so2":"3","co":"0.0","o3":"103"},{"pubTime":"2021-06-02T16:00+08:00","name":"古城","id":"CNA1012","aqi":"32","level":"1","category":"优","primary":"NA","pm10":"30","pm2p5":"4","no2":"6","so2":"3","co":"0.2","o3":"100"},{"pubTime":"2021-06-02T16:00+08:00","name":"昌平镇","id":"CNA1010","aqi":"32","level":"1","category":"优","primary":"NA","pm10":"25","pm2p5":"5","no2":"7","so2":"2","co":"0.3","o3":"102"},{"pubTime":"2021-06-02T16:00+08:00","name":"顺义新城","id":"CNA1008","aqi":"46","level":"1","category":"优","primary":"NA","pm10":"33","pm2p5":"20","no2":"4","so2":"1","co":"0.6","o3":"145"},{"pubTime":"2021-06-02T16:00+08:00","name":"海淀区万柳","id":"CNA1007","aqi":"44","level":"1","category":"优","primary":"NA","pm10":"44","pm2p5":"7","no2":"13","so2":"2","co":"0.2","o3":"97"},{"pubTime":"2021-06-02T16:00+08:00","name":"东四","id":"CNA1003","aqi":"34","level":"1","category":"优","primary":"NA","pm10":"33","pm2p5":"4","no2":"5","so2":"1","co":"0.3","o3":"106"},{"pubTime":"2021-06-02T16:00+08:00","name":"定陵","id":"CNA1002","aqi":"31","level":"1","category":"优","primary":"NA","pm10":"9","pm2p5":"2","no2":"6","so2":"3","co":"0.1","o3":"99"}]
     * refer : {"sources":["cnemc"],"license":["no commercial use"]}
     */

    private String code;
    private String updateTime;
    private String fxLink;
    private NowBean now;
    private ReferBean refer;
    private List<StationBean> station;

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

    public List<StationBean> getStation() {
        return station;
    }

    public void setStation(List<StationBean> station) {
        this.station = station;
    }

    public static class NowBean implements Serializable {
        /**
         * pubTime : 2021-06-02T16:00+08:00
         * aqi : 38
         * level : 1
         * category : 优
         * primary : NA
         * pm10 : 38
         * pm2p5 : 11
         * no2 : 9
         * so2 : 2
         * co : 0.3
         * o3 : 110
         */

        private String pubTime;
        private String aqi;
        private String level;
        private String category;
        private String primary;
        private String pm10;
        private String pm2p5;
        private String no2;
        private String so2;
        private String co;
        private String o3;

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPrimary() {
            return primary;
        }

        public void setPrimary(String primary) {
            this.primary = primary;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm2p5() {
            return pm2p5;
        }

        public void setPm2p5(String pm2p5) {
            this.pm2p5 = pm2p5;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }

        @Override
        public String toString() {
            return "NowBean{" +
                    "pubTime='" + pubTime + '\'' +
                    ", aqi='" + aqi + '\'' +
                    ", level='" + level + '\'' +
                    ", category='" + category + '\'' +
                    ", primary='" + primary + '\'' +
                    ", pm10='" + pm10 + '\'' +
                    ", pm2p5='" + pm2p5 + '\'' +
                    ", no2='" + no2 + '\'' +
                    ", so2='" + so2 + '\'' +
                    ", co='" + co + '\'' +
                    ", o3='" + o3 + '\'' +
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

    public static class StationBean implements Serializable {
        /**
         * pubTime : 2021-06-02T16:00+08:00
         * name : 丰台小屯
         * id : CNA3696
         * aqi : 32
         * level : 1
         * category : 优
         * primary : NA
         * pm10 : 26
         * pm2p5 : 9
         * no2 : 11
         * so2 : 1
         * co : 0.3
         * o3 : 100
         */

        private String pubTime;
        private String name;
        private String id;
        private String aqi;
        private String level;
        private String category;
        private String primary;
        private String pm10;
        private String pm2p5;
        private String no2;
        private String so2;
        private String co;
        private String o3;

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPrimary() {
            return primary;
        }

        public void setPrimary(String primary) {
            this.primary = primary;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm2p5() {
            return pm2p5;
        }

        public void setPm2p5(String pm2p5) {
            this.pm2p5 = pm2p5;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }

        @Override
        public String toString() {
            return "StationBean{" +
                    "pubTime='" + pubTime + '\'' +
                    ", name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    ", aqi='" + aqi + '\'' +
                    ", level='" + level + '\'' +
                    ", category='" + category + '\'' +
                    ", primary='" + primary + '\'' +
                    ", pm10='" + pm10 + '\'' +
                    ", pm2p5='" + pm2p5 + '\'' +
                    ", no2='" + no2 + '\'' +
                    ", so2='" + so2 + '\'' +
                    ", co='" + co + '\'' +
                    ", o3='" + o3 + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AirNowConditionResponse{" +
                "code='" + code + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", fxLink='" + fxLink + '\'' +
                ", now=" + now +
                ", refer=" + refer +
                ", station=" + station +
                '}';
    }
}



