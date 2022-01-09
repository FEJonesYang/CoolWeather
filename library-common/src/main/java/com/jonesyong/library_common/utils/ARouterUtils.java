package com.jonesyong.library_common.utils;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jonesyong.library_common.base.Router;

import org.jetbrains.annotations.NotNull;

/**
 * @Author JonesYang
 * @Date 2022-01-08
 * @Description 配置路由跳转的工具类
 */
public class ARouterUtils {


    // 不带参数，跳转到几个模块的首次启动界面
    public static void navigationToHome() {
        navigation(Router.MODULE_HOME_WEATHER_ACTIVITY);
    }

    public static void navigationToDetail() {
        navigation(Router.MODULE_DETAIL_DETAIL_ACTIVITY);
    }


    public static void navigationToMap() {
        navigation(Router.MODULE_MAP_MAP_ACTIVITY);
    }

    public static void navigationToSearch() {
        navigation(Router.MODULE_SEARCH_SEARCH_ACTIVITY);
    }

    public static void navigationWithBundle(@NotNull String path, String key, Bundle bundle) {
        getInstance().build(path).withBundle(key, bundle).navigation();
    }

    public static void navigation(@NotNull String path) {
        getInstance().build(path).navigation();
    }

    private static ARouter getInstance() {
        return ARouter.getInstance();
    }
}
