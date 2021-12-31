package com.example.kuou.base;

import android.app.Application;



/**
 * @author JonesYang
 * @Data 2021-06-06
 * @Function 自定义的 Application
 */
public class CoolWeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
    }
}
