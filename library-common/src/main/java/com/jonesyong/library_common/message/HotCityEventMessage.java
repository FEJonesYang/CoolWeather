package com.jonesyong.library_common.message;


import com.jonesyong.library_common.model.HotCityListModel;

/**
 * @Author: JonesYang
 * @Date Created in 2021-06-09
 * @Description: 发送热点城市的数据的 Message
 */
public class HotCityEventMessage {

    private HotCityListModel mHotCityListModel;

    public HotCityEventMessage(HotCityListModel hotCityListModel) {
        this.mHotCityListModel = hotCityListModel;
    }

    public HotCityListModel getHotCityListModel() {
        return mHotCityListModel;
    }
}
