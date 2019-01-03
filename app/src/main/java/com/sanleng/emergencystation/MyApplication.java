package com.sanleng.emergencystation;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.baidu.mapapi.SDKInitializer;

public class MyApplication extends MultiDexApplication {

    private static Application instance;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
//        JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this); // 初始化 JPush
        instance = this;
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(this);
    }
}
