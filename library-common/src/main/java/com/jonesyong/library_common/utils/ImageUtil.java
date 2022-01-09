package com.jonesyong.library_common.utils;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.jonesyong.library_common.R;

/**
 * @author JonesYang
 * @Data 2021-04-13
 * @Function 基于Glide二次封装
 */
public class ImageUtil {
    /**
     * (1)
     * 显示图片ImageView
     *
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 组件
     */
    public static void showImageView(Context context, String url,
                                     ImageView imageView) {
        Glide.with(context).load(url)// 加载图片
                .error(R.drawable.ic_launcher_foreground)// 设置错误图片
                .placeholder(R.drawable.ic_launcher_foreground)// 设置占位图
                .into(imageView);

    }

    /**
     * （2）
     * 显示图片 圆角显示  ImageView
     *
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 组件
     */
    public static void showImageViewToCircle(Application context, String url, ImageView imageView) {
        Glide.with(context).load(url)
                // 加载图片
                .error(R.drawable.ic_launcher_foreground)
                // 设置错误图片
                .placeholder(R.drawable.ic_launcher_foreground)
                // 设置占位图
                .apply(RequestOptions.bitmapTransform(new CircleCrop())) // 圆角
                .into(imageView);

    }

    /**
     * （3）
     * 显示图片 圆角矩形显示  ImageView
     *
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 组件
     * @param radius    圆角矩形的弧度
     */
    public static void showImageViewToRoundedCorners(Application context, String url, ImageView imageView, int radius) {
        Glide.with(context).load(url)
                // 加载图片
                .error(R.drawable.ic_launcher_foreground)
                // 设置错误图片
                .placeholder(R.drawable.ic_launcher_foreground)
                // 设置占位图
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius))) // 圆角
                .into(imageView);

    }

}
