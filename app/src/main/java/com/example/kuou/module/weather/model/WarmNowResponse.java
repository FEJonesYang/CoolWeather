package com.example.kuou.module.weather.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author JonesYang
 * @Data 2021-06-01
 * @Function 预警信息的实体数据
 */
public class WarmNowResponse {

    /**
     * code : 200
     * updateTime : 2021-06-01T13:45+08:00
     * fxLink : http://hfx.link/2bc5
     * warning : [{"id":"10102010020210601084000570690941","sender":"上海中心气象台","pubTime":"2021-06-01T08:40+08:00","title":"上海中心气象台发布大风蓝色预警[Ⅳ级/一般]","status":"active","level":"蓝色","type":"11B06","typeName":"大风","text":"上海中心气象台2021年06月01日08时40分发布大风蓝色预警[Ⅳ级/一般]：受低压东移影响，预计未来24小时内本市将出现5级阵风6-7级的东南大风，沿江沿海地区和江河湖面最大阵风可达7-8级，请注意防范。","related":""}]
     * refer : {"sources":["12379","Weather China"],"license":["no commercial use"]}
     */

    private String code;
    private String updateTime;
    private String fxLink;
    private ReferBean refer;
    private List<WarningBean> warning;

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

    public ReferBean getRefer() {
        return refer;
    }

    public void setRefer(ReferBean refer) {
        this.refer = refer;
    }

    public List<WarningBean> getWarning() {
        return warning;
    }

    public void setWarning(List<WarningBean> warning) {
        this.warning = warning;
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

    public static class WarningBean implements Serializable {
        /**
         * id : 10102010020210601084000570690941
         * sender : 上海中心气象台
         * pubTime : 2021-06-01T08:40+08:00
         * title : 上海中心气象台发布大风蓝色预警[Ⅳ级/一般]
         * status : active
         * level : 蓝色
         * type : 11B06
         * typeName : 大风
         * text : 上海中心气象台2021年06月01日08时40分发布大风蓝色预警[Ⅳ级/一般]：受低压东移影响，预计未来24小时内本市将出现5级阵风6-7级的东南大风，沿江沿海地区和江河湖面最大阵风可达7-8级，请注意防范。
         * related :
         */

        private String id;
        private String sender;
        private String pubTime;
        private String title;
        private String status;
        private String level;
        private String type;
        private String typeName;
        private String text;
        private String related;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getRelated() {
            return related;
        }

        public void setRelated(String related) {
            this.related = related;
        }
    }
}
