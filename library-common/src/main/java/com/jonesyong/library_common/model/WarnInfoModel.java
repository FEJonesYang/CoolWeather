package com.jonesyong.library_common.model;

import java.io.Serializable;

/**
 * @Author JonesYang
 * @Date 2022-01-11
 * @Description
 */
public class WarnInfoModel implements Serializable {
    private String sender;

    private String title;

    private String level;

    private String typeName;

    private String text;

    public String getSender() {
        return sender;
    }

    public String getTitle() {
        return title;
    }

    public String getLevel() {
        return level;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getText() {
        return text;
    }
}
