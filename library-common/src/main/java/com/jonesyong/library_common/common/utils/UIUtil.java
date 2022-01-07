package com.jonesyong.library_common.common.utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.jonesyong.library_common.R;


/**
 * @Author JonesYang
 * @Date 2021-10-22
 * @Description UI 展示的工具类
 */
public class UIUtil {

    public static void showShortToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }


    /**
     * 网络请求错误展示的View
     */
    public static void showErrorView(Context context) {
        getErrorView(context).setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏网络请求错误展示的View
     */
    public static void hideErrorView(Context context) {
        getErrorView(context).setVisibility(View.VISIBLE);
    }

    /**
     * 空界面
     */
    public static void showEmptyView() {
    }

    /**
     * 空界面
     */
    public static void hideEmptyView() {
    }

    /**
     * 没有更多了
     */
    public static void showNoMoreView() {
    }

    private static View getErrorView(Context context) {
        View errorView = LayoutInflater.from(context).inflate(R.layout.error_view, null);
        return errorView.findViewById(R.id.container_error_view);
    }
}
