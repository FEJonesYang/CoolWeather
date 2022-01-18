package com.jonesyong.library_common.message;

import com.jonesyong.library_common.model.LocationModel;

/**
 * @Author JonesYang
 * @Date 2022-01-04
 * @Description
 */
public class SearchCityEvent {

    private final LocationModel mLocationModel;

    public SearchCityEvent(LocationModel location) {
        this.mLocationModel  = location;
    }

    public LocationModel getLocationModel() {
        return mLocationModel;
    }
}
