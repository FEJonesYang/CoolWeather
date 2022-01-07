package com.jonesyong.library_common.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author JonesYang
 * @Data 2021-05-28
 * @Function
 */
public class SearchCityBean {


    /**
     * code : 200
     * location : [{"name":"重庆","id":"101040100","lat":"29.56376","lon":"106.55046","adm2":"重庆","adm1":"重庆市","country":"中国","tz":"Asia/Shanghai","utcOffset":"+08:00","isDst":"0","type":"city","rank":"11","fxLink":"http://hfx.link/2c11"},{"name":"合川","id":"101040300","lat":"29.99099","lon":"106.26555","adm2":"重庆","adm1":"重庆市","country":"中国","tz":"Asia/Shanghai","utcOffset":"+08:00","isDst":"0","type":"city","rank":"23","fxLink":"http://hfx.link/2c31"},{"name":"涪陵","id":"101041400","lat":"29.70365","lon":"107.39490","adm2":"重庆","adm1":"重庆市","country":"中国","tz":"Asia/Shanghai","utcOffset":"+08:00","isDst":"0","type":"city","rank":"23","fxLink":"http://hfx.link/2cd1"},{"name":"云阳","id":"101041700","lat":"30.93052","lon":"108.69770","adm2":"重庆","adm1":"重庆市","country":"中国","tz":"Asia/Shanghai","utcOffset":"+08:00","isDst":"0","type":"city","rank":"23","fxLink":"http://hfx.link/2cg1"},{"name":"永川","id":"101040200","lat":"29.34874","lon":"105.89471","adm2":"重庆","adm1":"重庆市","country":"中国","tz":"Asia/Shanghai","utcOffset":"+08:00","isDst":"0","type":"city","rank":"23","fxLink":"http://hfx.link/2c21"},{"name":"九龙坡","id":"101043900","lat":"29.52349","lon":"106.48098","adm2":"重庆","adm1":"重庆市","country":"中国","tz":"Asia/Shanghai","utcOffset":"+08:00","isDst":"0","type":"city","rank":"25","fxLink":"http://hfx.link/1tk61"},{"name":"江津","id":"101040500","lat":"29.28338","lon":"106.25315","adm2":"重庆","adm1":"重庆市","country":"中国","tz":"Asia/Shanghai","utcOffset":"+08:00","isDst":"0","type":"city","rank":"25","fxLink":"http://hfx.link/2c51"},{"name":"万州","id":"101041300","lat":"30.80780","lon":"108.38024","adm2":"重庆","adm1":"重庆市","country":"中国","tz":"Asia/Shanghai","utcOffset":"+08:00","isDst":"0","type":"city","rank":"25","fxLink":"http://hfx.link/2cc1"},{"name":"开州","id":"101044100","lat":"31.16773","lon":"108.41331","adm2":"重庆","adm1":"重庆市","country":"中国","tz":"Asia/Shanghai","utcOffset":"+08:00","isDst":"0","type":"city","rank":"25","fxLink":"http://hfx.link/1tk81"},{"name":"渝北","id":"101040700","lat":"29.60145","lon":"106.51284","adm2":"重庆","adm1":"重庆市","country":"中国","tz":"Asia/Shanghai","utcOffset":"+08:00","isDst":"0","type":"city","rank":"25","fxLink":"http://hfx.link/2c71"}]
     * refer : {"sources":["qweather.com"],"license":["commercial license"]}
     */

    private String code;
    private ReferBean refer;
    private List<LocationBean> location;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ReferBean getRefer() {
        return refer;
    }

    public void setRefer(ReferBean refer) {
        this.refer = refer;
    }

    public List<LocationBean> getLocation() {
        return location;
    }

    public void setLocation(List<LocationBean> location) {
        this.location = location;
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
    }

    public static class LocationBean implements Serializable {
        /**
         * name : 重庆
         * id : 101040100
         * lat : 29.56376
         * lon : 106.55046
         * adm2 : 重庆
         * adm1 : 重庆市
         * country : 中国
         * tz : Asia/Shanghai
         * utcOffset : +08:00
         * isDst : 0
         * type : city
         * rank : 11
         * fxLink : http://hfx.link/2c11
         */

        private String name;
        private String id;
        private String lat;
        private String lon;
        private String adm2;
        private String adm1;
        private String country;
        private String tz;
        private String utcOffset;
        private String isDst;
        private String type;
        private String rank;
        private String fxLink;

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

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getAdm2() {
            return adm2;
        }

        public void setAdm2(String adm2) {
            this.adm2 = adm2;
        }

        public String getAdm1() {
            return adm1;
        }

        public void setAdm1(String adm1) {
            this.adm1 = adm1;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getTz() {
            return tz;
        }

        public void setTz(String tz) {
            this.tz = tz;
        }

        public String getUtcOffset() {
            return utcOffset;
        }

        public void setUtcOffset(String utcOffset) {
            this.utcOffset = utcOffset;
        }

        public String getIsDst() {
            return isDst;
        }

        public void setIsDst(String isDst) {
            this.isDst = isDst;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getFxLink() {
            return fxLink;
        }

        public void setFxLink(String fxLink) {
            this.fxLink = fxLink;
        }
    }

}
