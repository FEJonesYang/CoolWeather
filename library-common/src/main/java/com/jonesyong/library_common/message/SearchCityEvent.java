package com.jonesyong.library_common.message;

import com.jonesyong.library_common.model.SearchCityBean;

/**
 * @Author JonesYang
 * @Date 2022-01-04
 * @Description
 */
public class SearchCityEvent {

    private SearchCityBean.LocationBean locationBean;

    public SearchCityEvent(SearchCityBean.LocationBean locationBean) {
        this.locationBean = locationBean;
    }

    public SearchCityBean.LocationBean getLocationBean() {
        return locationBean;
    }
}
