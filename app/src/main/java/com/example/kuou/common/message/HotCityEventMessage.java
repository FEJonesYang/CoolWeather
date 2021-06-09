package com.example.kuou.common.message;

import com.example.kuou.module.search.model.HotCityResponse;

/**
 * @Author: JonesYang
 * @Date Created in 2021-06-09
 * @Description: 发送热点城市的数据的 Message
 */
public class HotCityEventMessage {

    private HotCityResponse hotCityResponse;

    // 点击的是哪一个item
    private int index;

    public HotCityEventMessage() {
    }

    public HotCityEventMessage(HotCityResponse hotCityResponse, int index) {
        this.hotCityResponse = hotCityResponse;
        this.index = index;
    }

    public HotCityResponse getHotCityResponse() {
        return hotCityResponse;
    }

    public void setHotCityResponse(HotCityResponse hotCityResponse) {
        this.hotCityResponse = hotCityResponse;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
