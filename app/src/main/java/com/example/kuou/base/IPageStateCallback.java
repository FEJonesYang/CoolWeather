package com.example.kuou.base;

/**
 * @Author JonesYang
 * @Date 2021-12-12
 * @Description 空界面、错误界面的状态回调，如果没有数据或者网络出现问题，会被回调
 */
public interface IPageStateCallback {
    void showEmptyPage();

    void showErrorPage();
}
