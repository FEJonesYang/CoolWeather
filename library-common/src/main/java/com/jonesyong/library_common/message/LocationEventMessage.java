package com.jonesyong.library_common.message;

import com.amap.api.location.AMapLocation;

/**
 * @author JonesYang
 * @Data 2021-06-07
 * @Function 用于进行位置信息传递的 Message，初始化定位系统
 */
public class LocationEventMessage {
    private AMapLocation mAMapLocation;

    public LocationEventMessage(AMapLocation AMapLocation) {
        mAMapLocation = AMapLocation;
    }

    public AMapLocation getAMapLocation() {
        return mAMapLocation;
    }

    public void setAMapLocation(AMapLocation AMapLocation) {
        mAMapLocation = AMapLocation;
    }

}
