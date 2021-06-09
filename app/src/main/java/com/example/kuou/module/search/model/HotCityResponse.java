package com.example.kuou.module.search.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: JonesYang
 * @Date Created in 2021-06-09-9:17 上午
 * @Description:
 */
public class HotCityResponse {

    private String code;

    private ReferDTO refer;
    private List<TopCityListDTO> topCityList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ReferDTO getRefer() {
        return refer;
    }

    public void setRefer(ReferDTO refer) {
        this.refer = refer;
    }

    public List<TopCityListDTO> getTopCityList() {
        return topCityList;
    }

    public void setTopCityList(List<TopCityListDTO> topCityList) {
        this.topCityList = topCityList;
    }

    public static class ReferDTO implements Serializable {
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
            return "ReferDTO{" +
                    "sources=" + sources +
                    ", license=" + license +
                    '}';
        }
    }

    public static class TopCityListDTO implements Serializable {
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

        @Override
        public String toString() {
            return "TopCityListDTO{" +
                    "name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    ", lat='" + lat + '\'' +
                    ", lon='" + lon + '\'' +
                    ", adm2='" + adm2 + '\'' +
                    ", adm1='" + adm1 + '\'' +
                    ", country='" + country + '\'' +
                    ", tz='" + tz + '\'' +
                    ", utcOffset='" + utcOffset + '\'' +
                    ", isDst='" + isDst + '\'' +
                    ", type='" + type + '\'' +
                    ", rank='" + rank + '\'' +
                    ", fxLink='" + fxLink + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HotCityData{" +
                "code='" + code + '\'' +
                ", refer=" + refer +
                ", topCityList=" + topCityList +
                '}';
    }
}
